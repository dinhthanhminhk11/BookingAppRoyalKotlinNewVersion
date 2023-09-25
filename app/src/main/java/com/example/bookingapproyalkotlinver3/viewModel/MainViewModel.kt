package com.example.bookingapproyalkotlinver3.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.model.user.LoginResponse
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import com.example.bookingapproyalkotlinver3.data.repository.Repository
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application, private val repository: Repository
) : ViewModel() {
    val resourceMutableLiveDataHotelNearBy: MutableLiveData<Resource<HotelResponseNearBy>> =
        MutableLiveData()
    val addressData: MutableLiveData<String> = MutableLiveData()
    val ctyData: MutableLiveData<String> = MutableLiveData()
    val listAllHotelMutableLiveData: MutableLiveData<Resource<HotelResponse>> = MutableLiveData()
    val loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    fun getListNearByHotel(locationNearByRequest: LocationNearByRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = repository.getListHotelNearBy(locationNearByRequest)
                    resourceMutableLiveDataHotelNearBy.postValue(apiResult)
                    Log.e("MInh", "getListNearByHotel")
                } else {
                    resourceMutableLiveDataHotelNearBy.postValue(Resource.Error("Internet is not available"))
                }

            } catch (e: Exception) {
                resourceMutableLiveDataHotelNearBy.postValue(Resource.Error(e.message.toString()))
            }
        }

    fun login(userLogin: UserLogin) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getUser(userLogin)
                loginResponse.postValue(apiResult)
            } else {
                loginResponse.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            loginResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getUserByToken(token: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getUserByToken(token)
                apiResult.data?.let { UserClient.setUserFromUser(it.user) }
            }
        } catch (e: Exception) {
            loginResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getAllListHotel() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getAllListHotel()
                listAllHotelMutableLiveData.postValue(apiResult)
            } else {
                resourceMutableLiveDataHotelNearBy.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            resourceMutableLiveDataHotelNearBy.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {
        val locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                LocationServices.getFusedLocationProviderClient(context.applicationContext)
                    .removeLocationUpdates(this)
                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                    val latestlocIndex = locationResult.locations.size - 1
                    val lati = locationResult.locations[latestlocIndex].latitude
                    val longi = locationResult.locations[latestlocIndex].longitude
                    val locationYouSelf = Location("locationYourSelf")
                    locationYouSelf.longitude = longi
                    locationYouSelf.latitude = lati
                    getListNearByHotel(
                        LocationNearByRequest(
                            locationYouSelf.longitude, locationYouSelf.latitude, 10000
                        )
                    )
                    getAddress(context, locationYouSelf.latitude, locationYouSelf.longitude)
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun getAddress(context: Context, lati: Double, longi: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            geocoder.getFromLocation(lati, longi, 1).let {
                val obj = it!![0]
                var add = obj.getAddressLine(0)
                Log.e("MinhLocation", add)
                add = "$add\n${obj.countryName}"
                add = "$add\n${obj.countryCode}"
                add = "$add\n${obj.adminArea}"
                add = "$add\n${obj.postalCode}"
                add = "$add\n${obj.subAdminArea}"
                add = "$add\n${obj.locality}"
                add = "$add\n${obj.subThoroughfare}"
                addressData.postValue(add)
                ctyData.postValue(obj.adminArea)
                Log.v("IGA", "Address$add")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}