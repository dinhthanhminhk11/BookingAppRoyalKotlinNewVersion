package com.example.bookingapproyalkotlinver3.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapproyalkotlinver3.data.model.bookmark.BookmarkResponse
import com.example.bookingapproyalkotlinver3.data.model.bookmark.PostIDUserAndIdHouse
import com.example.bookingapproyalkotlinver3.data.model.feedback.DataFeedBack
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.model.hotel.Room
import com.example.bookingapproyalkotlinver3.data.model.notification.NotiResponse
import com.example.bookingapproyalkotlinver3.data.model.user.LoginResponse
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import com.example.bookingapproyalkotlinver3.data.repository.Repository
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.data.util.view.isNetworkAvailable
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
    val notificationResponse: MutableLiveData<Resource<NotiResponse>> = MutableLiveData()
    val bookmarkResponse: MutableLiveData<Resource<BookmarkResponse>> = MutableLiveData()
    val bookmarkCheckHotelResponse: MutableLiveData<Resource<BookmarkResponse>> = MutableLiveData()
    val hotelResponse: MutableLiveData<Resource<HotelById>> = MutableLiveData()
    val dataFeedBack: MutableLiveData<Resource<DataFeedBack>> = MutableLiveData()
    val locationYouSelfMutableLiveData: MutableLiveData<Location> = MutableLiveData()
    val roomResponseMutableLiveData: MutableLiveData<Resource<Room>> = MutableLiveData()
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

    fun updateNotification(id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                repository.updateNotificationSeen(id)
            }
        } catch (e: Exception) {
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
                listAllHotelMutableLiveData.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            listAllHotelMutableLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getListNotification(idUser: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getListNotification(idUser)
                notificationResponse.postValue(apiResult)
            } else {
                notificationResponse.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            notificationResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getListBookmarkByUser(idUser: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getListBookmarkByUser(idUser)
                bookmarkResponse.postValue(apiResult)
            } else {
                bookmarkResponse.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            bookmarkResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getBookmarkByIdUserAndIdHouse(idUser: String, idHotel: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = repository.getBookmarkByIdUserAndIdHouse(idUser, idHotel)
                    bookmarkCheckHotelResponse.postValue(apiResult)
                } else {
                    bookmarkCheckHotelResponse.postValue(Resource.Error("Internet is not available"))
                }

            } catch (e: Exception) {
                bookmarkCheckHotelResponse.postValue(Resource.Error(e.message.toString()))
            }
        }

    fun getHotelById(idHotel: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getHotelById(idHotel)
                hotelResponse.postValue(apiResult)
            } else {
                hotelResponse.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            hotelResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getListFeedBack(idHotel: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getFeedBack(idHotel)
                dataFeedBack.postValue(apiResult)
            } else {
                dataFeedBack.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            dataFeedBack.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun addBookmark(postIDUserAndIdHouse: PostIDUserAndIdHouse) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNetworkAvailable(app)) {
                    repository.addBookmark(postIDUserAndIdHouse)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun deleteBookmark(idUser: String, idHotel: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                repository.deleteBookmark(idUser, idHotel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getRoomById(idRoom: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = repository.getRoomById(idRoom)
                roomResponseMutableLiveData.postValue(apiResult)
            } else {
                roomResponseMutableLiveData.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            roomResponseMutableLiveData.postValue(Resource.Error(e.message.toString()))
        }
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
                    locationYouSelfMutableLiveData.postValue(locationYouSelf)
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
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}