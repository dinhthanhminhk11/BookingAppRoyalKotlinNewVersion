package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentNearByFromYouBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemNearFromYouMapBinding
import com.example.bookingapproyalkotlinver3.ui.fragment.DetailHotelFragment
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
import java.text.DecimalFormat


@AndroidEntryPoint
class NearByFromYouFragment : BaseViewModelFragment<FragmentNearByFromYouBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var address: String
    private lateinit var iconGenerator: IconGenerator
    private val TRANSPARENT_DRAWABLE = ColorDrawable(Color.TRANSPARENT)
    private lateinit var markerView: View
    private lateinit var priceTag: TextView
    private lateinit var latLngLocationYourSelf : LatLng
    private val callback = OnMapReadyCallback { googleMap ->
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap = googleMap
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNearByFromYouBinding =
        FragmentNearByFromYouBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        iconGenerator = IconGenerator(requireActivity())
        markerView = LayoutInflater.from(requireActivity()).inflate(R.layout.marker, null)
        priceTag = markerView.findViewById<TextView>(R.id.priceTag)

        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerview)
        iconGenerator.setBackground(TRANSPARENT_DRAWABLE)
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {
        viewModel.ctyData.observe(viewLifecycleOwner) {
            binding.toolBar.title = it.toString()
            binding.progressBar.visibility = View.GONE
        }

        viewModel.addressData.observe(viewLifecycleOwner) {
            binding.toolBar.subtitle = it.toString()
            binding.progressBar.visibility = View.GONE
            address = it.toString()
        }
        viewModel.locationYouSelfMutableLiveData.observe(viewLifecycleOwner) {
            showMakerAndText(LatLng(it.latitude, it.longitude))
            binding.progressBar.visibility = View.GONE
        }

        viewModel.resourceMutableLiveDataHotelNearBy.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { it ->
                        binding.recyclerview.apply {
                            adapter = NearFromYouAdapterMap(it.data)
                            layoutManager = LinearLayoutManager(
                                requireActivity(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            itemAnimator = DefaultItemAnimator()
                            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                                override fun onScrollStateChanged(
                                    recyclerView: RecyclerView,
                                    newState: Int
                                ) {
                                    super.onScrollStateChanged(recyclerView, newState)
                                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                        val position = getCurrentItem()
                                        val dataMap = it.data[position]
                                        // mapRepository.getRootDistanceAndDuration(nameLocationYourSelf, dataMap.getData().getNameLocation(), binding.distance, binding.time)
                                        selectCamera(dataMap)
                                    }
                                }
                            })
                        }
                        it.data.forEach { item ->
                            drawMarkerListDataHouse(item)
                        }
                    }
                }

                is Resource.Error -> {
                    it.message?.let {

                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    override fun initData() {
        if (checkLocationPermission()) {
            viewModel.getCurrentLocation(requireActivity())
        } else {
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        handlePermissionsResult(requestCode, permissions, grantResults)
    }

    private fun handlePermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == HomeFragment.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getCurrentLocation(requireActivity())
            } else {
                binding.toolBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showMakerAndText(latLng: LatLng) {
        if (::mMap.isInitialized) {

            latLngLocationYourSelf = latLng

            val myLocation = LatLng(latLng.latitude, latLng.longitude)

            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLocation, 13f)
            mMap.animateCamera(cameraUpdate)

            val cameraPosition =
                CameraPosition.Builder().target(myLocation).zoom(90f).bearing(0f).tilt(40f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            val markerOptions =
                MarkerOptions().position(myLocation).title(getString(R.string.Your_location))
                    .snippet(address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            mMap.addMarker(markerOptions)!!

            mMap.setOnMarkerClickListener { marker ->
                if (marker.position.latitude == latLngLocationYourSelf.latitude && marker.position.longitude == latLngLocationYourSelf.longitude) {
                    false
                } else {
                    binding.recyclerview.smoothScrollToPosition(Integer.parseInt(marker.id.substring(1)) - 1)
                    false
                }
            }
        }
    }

    inner class NearFromYouAdapterMap(private val hotelList: List<Hotel>) :
        RecyclerView.Adapter<NearFromYouAdapterMap.ViewHolder>() {
        private val decimalFormat = DecimalFormat("#.#")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemNearFromYouMapBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(hotelList[position])

        }

        override fun getItemCount(): Int {
            return hotelList.size
        }

        inner class ViewHolder(val binding: ItemNearFromYouMapBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: Hotel) {
                loadImage(binding.imageHotel.context, item.images[0], binding.imageHotel)

                binding.name.text = item.name
                binding.address.text = "${item.sonha}, ${item.xa}, ${item.huyen}, ${item.tinh}"
//                binding.name.setTextColor(color)
//                binding.address.setTextColor(color)
//                binding.contentCard.setCardBackgroundColor(background)
                binding.nameCategory.text =
                    "Cách ${decimalFormat.format(item.calculated / 1000)} Km"

                when (item.TbSao.toInt()) {
                    1 -> {
                        binding.imageStar2.visibility = View.INVISIBLE
                        binding.imageStar3.visibility = View.INVISIBLE
                        binding.imageStar4.visibility = View.INVISIBLE
                        binding.imageStar5.visibility = View.INVISIBLE
                    }

                    2 -> {
                        binding.imageStar3.visibility = View.INVISIBLE
                        binding.imageStar4.visibility = View.INVISIBLE
                        binding.imageStar5.visibility = View.INVISIBLE
                    }

                    3 -> {
                        binding.imageStar4.visibility = View.INVISIBLE
                        binding.imageStar5.visibility = View.INVISIBLE
                    }

                    4 -> {
                        binding.imageStar5.visibility = View.INVISIBLE
                    }
                }

                binding.price.text = item.giaDaoDong
                binding.contentCard.setOnClickListener {
                    val bundle = Bundle().apply {
                        putSerializable(AppConstant.HOTEL_EXTRA, item._id)
                    }
                    findNavController().navigate(
                        R.id.action_nearByFromYouFragment_to_detailHotelFragment, bundle
                    )
                }
            }
        }
    }

    private fun getCurrentItem(): Int {
        return (binding.recyclerview.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    private fun selectCamera(hotel: Hotel) {
        val latLng1 = LatLng(hotel.location.coordinates[1], hotel.location.coordinates[0])
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13f))
        val cameraPosition = CameraPosition.Builder()
            .target(latLng1)
            .zoom(15f)
            .bearing(0f)
            .tilt(40f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun drawMarkerListDataHouse(hotel: Hotel) {
        priceTag.text = hotel.giaDaoDong
        val latLng = LatLng(hotel.location.coordinates[1], hotel.location.coordinates[0])
        iconGenerator.setContentView(markerView)
        iconGenerator.setTextAppearance(R.style.iconGenText)
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title(hotel.name)
            .snippet("${hotel.sonha}, ${hotel.xa}, ${hotel.huyen}, ${hotel.tinh}")
            .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon()))
        mMap.addMarker(markerOptions)
    }
}