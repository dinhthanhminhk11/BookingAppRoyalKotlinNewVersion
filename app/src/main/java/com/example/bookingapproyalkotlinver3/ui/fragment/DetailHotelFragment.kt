package com.example.bookingapproyalkotlinver3.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.MySharedPreferences
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.data.model.bookmark.PostIDUserAndIdHouse
import com.example.bookingapproyalkotlinver3.data.model.feedback.DataFeedBack
import com.example.bookingapproyalkotlinver3.data.model.feedback.FeedBack
import com.example.bookingapproyalkotlinver3.data.model.hotel.Convenient
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.hotel.Room
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentDetailHotelActivityBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemConvenientBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemFeedbackBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemGalleryBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemRoomHotelBinding
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.IndicatorView.animation.type.IndicatorAnimationType
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.SliderAnimations
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.SliderView
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.SliderViewAdapter
import com.example.bookingapproyalkotlinver3.ui.fragment.home.HomeFragment.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class DetailHotelFragment : BaseViewModelFragment<FragmentDetailHotelActivityBinding>(),
    OnMapReadyCallback {
    private val viewModel: MainViewModel by viewModels()
    private var isClickSpeed: Boolean = false
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var convenientAdapter: ConvenientAdapter
    private lateinit var roomHotelAdapter: RoomHotelAdapter
    private lateinit var feedbackAdapter: FeedbackAdapter
    private var idHotel: String = ""
    private lateinit var mMap: GoogleMap
    private var isMapReady = false
    override fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapinfo) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            mMap = googleMap
            isMapReady = true
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.showMore.setUnderlinedText(binding.showMore.text.toString())
        binding.showMedical.setUnderlinedText(binding.showMedical.text.toString())
        binding.btnShowFeedback.setUnderlinedText(binding.btnShowFeedback.text.toString())
    }

    override fun initOnClickListener() {
        binding.bookmark.setOnClickListener {
            isClickSpeed = !isClickSpeed
            val iconResource = if (isClickSpeed) {
                R.drawable.ic_baseline_bookmark_24_white_full
            } else {
                R.drawable.ic_baseline_bookmark_border_24_menu_toolbar

            }
            binding.bookmark.setImageResource(iconResource)
            val userId = UserClient.id.toString()
            if (isClickSpeed) {
                viewModel.addBookmark(PostIDUserAndIdHouse(userId, idHotel))
            } else {
                viewModel.deleteBookmark(userId, idHotel)
            }
        }

        binding.btnRentNow.setOnClickListener {
            val positionScroll = binding.contentCancellationPolicy.height
            val positionScroll2 = binding.contentHost.height
            val positionScroll3 = binding.contentConvenient.height
            val positionScroll4 = binding.contentImageHotel.height
            val positionScroll5 = binding.contentLocation.height
            val positionScroll6 = binding.contentFeedback.height
            val positionScroll7 = binding.contentOpenAndEndingHotel.height
            val positionScroll8 = binding.contentPolicy.height
            val positionScroll9 = binding.contentCancelBooking.height
            val positionScroll10 = binding.contentMedican.height
            val sum =
                positionScroll + positionScroll2 + positionScroll3 + positionScroll4 + positionScroll5 + positionScroll6 + positionScroll7 + positionScroll8 + positionScroll9 + positionScroll10

            binding.scrollView.post {
                binding.scrollView.scrollTo(0, sum)
            }
        }
    }

    override fun observeLiveData() {
        viewModel.hotelResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        binding.scrollView.visibility = View.VISIBLE
                        loadData(it)
                    }
                }

                is Resource.Loading -> {
                    it.message?.let {
                        binding.progressBar.visibility = View.VISIBLE
                        showSnackbar(requireView(), it)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.dataFeedBack.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        loadDataFeedback(it)
                    }
                }

                is Resource.Loading -> {
                    it.message?.let {
                        binding.progressBar.visibility = View.VISIBLE
                        showSnackbar(requireView(), it)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.bookmarkCheckHotelResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        if (it.data.isNotEmpty()) {
                            if (it.data[0].isCheck) {
                                binding.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24_white_full)
                                isClickSpeed = true
                            }
                        } else {
                            binding.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar)
                            isClickSpeed = false
                        }
                    }
                }

                is Resource.Loading -> {
                    it.message?.let {}
                }

                is Resource.Error -> {
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadDataFeedback(it: DataFeedBack) {
        feedbackAdapter = FeedbackAdapter(it.data)
        binding.rcvFeedback.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = feedbackAdapter
        }

        if (it.data.isNotEmpty()) {
            val total = it.data.sumBy { feedback -> feedback.sao }
            val average = total.toFloat() / it.data.size
            val decimalFormat = DecimalFormat("#.#")

            binding.btnDanhGia.text = "${it.data.size} " + getString(R.string.Assess)
            binding.tvTotalFeedback.text = "${it.data.size} " + getString(R.string.Assess)
            binding.btnDanhGia.setUnderlinedText(binding.btnDanhGia.text.toString())

            if (average % 1 == 0f) {
                binding.tvCountSao.text = decimalFormat.format(average) + ".0"
            } else {
                binding.tvCountSao.text = decimalFormat.format(average)
            }

//                            feedbackViewModel.updateSaoProduct(idHotel, average.toDouble())
        } else {
            binding.btnDanhGia.text = getString(R.string.Assess)
            binding.tvCountSao.text = "5.0"
            binding.tvTotalFeedback.text = "0 " + getString(R.string.Assess)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(it: HotelById) {
        loadImage(binding.ivimgHotel.context, it.dataHotel.images[0], binding.ivimgHotel)
        loadImage(binding.imgManage.context, it.dataUser.image, binding.imgManage)

        binding.tvNameHotel.text = it.dataHotel.name
        binding.tvAddress.text =
            "${it.dataHotel.sonha}, ${it.dataHotel.xa}, ${it.dataHotel.huyen}, ${it.dataHotel.tinh}"
        binding.tvSumDienTich.text = "${it.dataHotel.dienTich} m²"
        binding.priceRoom.text = it.dataHotel.giaDaoDong
        binding.ContentHouse.text = it.dataHotel.mota
        binding.tvTimeNhanPhong.text = it.dataHotel.timeDat
        binding.dayEnd.text = it.dataHotel.timeTra
        binding.textPolicy.text = it.dataHotel.chinhsach

        binding.NameManage.text = it.dataUser.name

        binding.cancelTrue.visibility = if (it.dataHotel.chinhSachHuy) View.VISIBLE else View.GONE
        binding.cancelFalse.visibility = if (!it.dataHotel.chinhSachHuy) View.VISIBLE else View.GONE
        galleryAdapter = GalleryAdapter(it.dataHotel.images)
        binding.rcvGallery.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = galleryAdapter
        }

        convenientAdapter = ConvenientAdapter(it.dataHotel.convenient)
        binding.rcvConvenient.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = convenientAdapter
        }
        roomHotelAdapter = RoomHotelAdapter(it.dataRoom)
        binding.rcvRoom.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomHotelAdapter
        }

        viewModel.getListFeedBack(it.dataHotel._id)
        if (!MySharedPreferences.getInstance(requireActivity())
                .getString(AppConstant.TOKEN_USER, "").isNullOrEmpty()
        ) {
            viewModel.getBookmarkByIdUserAndIdHouse(UserClient.id.toString(), it.dataHotel._id)
        }
        Handler().postDelayed({
            showMakerAndText(it.dataHotel)
        }, 1000)
    }

    override fun initData() {
        idHotel = arguments?.getString(AppConstant.HOTEL_EXTRA, "").toString()
        arguments?.let {
            viewModel.getHotelById(it.getString(AppConstant.HOTEL_EXTRA, ""))
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDetailHotelActivityBinding =
        FragmentDetailHotelActivityBinding.inflate(inflater, container, false)


    inner class GalleryAdapter(
        private val galleryList: List<String>
    ) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemGalleryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(galleryList[position], position)
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount(): Int {
            return if (galleryList.size < 4) galleryList.size else 4
        }

        inner class ViewHolder(val binding: ItemGalleryBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: String, position: Int) {
                loadImage(binding.ivimgHotel.context, item, binding.ivimgHotel)
                if (position == 3) {
                    if (galleryList.size - 4 == 0) {
                        binding.btnAmount.visibility = View.GONE
                    } else {
                        binding.btnAmount.visibility = View.VISIBLE
                        binding.btnAmount.text = "+${galleryList.size - 4}"
                    }
                }
            }
        }
    }

    inner class ConvenientAdapter(private val convenientList: List<Convenient>) :
        RecyclerView.Adapter<ConvenientAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemConvenientBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(convenientList[position], position)
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount(): Int {
            return if (convenientList.size < 5) convenientList.size else 5
        }

        inner class ViewHolder(val binding: ItemConvenientBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: Convenient, position: Int) {
                loadImage(binding.image.context, item.iconImage, binding.image)
                binding.name.text = item.name
            }
        }
    }

    inner class FeedbackAdapter(private val feedbackList: List<FeedBack>) :
        RecyclerView.Adapter<FeedbackAdapter.ViewHolder>() {
        private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemFeedbackBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(feedbackList[position], position)
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount(): Int {
            return feedbackList.size.coerceAtMost(5)
        }

        inner class ViewHolder(val binding: ItemFeedbackBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: FeedBack, position: Int) {
                val calendar = Calendar.getInstance()
                val time: Long = calendar.timeInMillis
                val date = format.format(time)
                try {
                    val b: Date = format.parse(date)
                    val txtTime = DateUtils.getRelativeTimeSpanString(
                        item.time.toLong(),
                        b.time,
                        DateUtils.MINUTE_IN_MILLIS * 1,
                        DateUtils.FORMAT_ABBREV_RELATIVE
                    )
                    binding.tvTime.text = txtTime
                    binding.tvContent.text = item.textUser
                    binding.tvShowFeedback.text = "Hiển thị tất cả ${feedbackList.size} đánh giá"
                    binding.tvShowFeedback.paintFlags =
                        binding.tvShowFeedback.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                val sao = item.sao
                val starImages = arrayOf(
                    binding.imgStar1,
                    binding.imgStar2,
                    binding.imgStar3,
                    binding.imgStar4,
                    binding.imgStar5
                )

                for (i in starImages.indices) {
                    if (i < sao) {
                        starImages[i].setImageResource(R.drawable.ic_star_click)
                    } else {
                        starImages[i].setImageResource(R.drawable.ic_star_no_click)
                    }
                }

                binding.tvNameUser.text = item.name
                loadImage(binding.imgUser.context, item.imgUser, binding.imgUser)

                if (position == feedbackList.size - 1 && position != 0) {
                    binding.tvShowFeedback.visibility = View.VISIBLE
                    binding.lnDetail.visibility = View.GONE
                    binding.lnSao.visibility = View.GONE
                    binding.tvContent.visibility = View.GONE
                }
                if (position == 4) {
                    if (feedbackList.size - 5 == 0) {
                        binding.tvShowFeedback.visibility = View.VISIBLE
                    } else {
                        binding.tvShowFeedback.visibility = View.VISIBLE
                        binding.lnDetail.visibility = View.GONE
                        binding.lnSao.visibility = View.GONE
                        binding.tvContent.visibility = View.GONE
                    }
                }
            }
        }
    }

    inner class RoomHotelAdapter(private val roomList: List<Room>) :
        RecyclerView.Adapter<RoomHotelAdapter.ViewHolder>() {
        private val fm = DecimalFormat("#,###")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemRoomHotelBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(roomList[position])
            holder.itemView.setOnClickListener {

            }
        }

        override fun getItemCount(): Int {
            return roomList.size
        }

        inner class ViewHolder(val binding: ItemRoomHotelBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(item: Room) {
                val imageAutoAdapter = ImageAutoSliderAdapter(item.images)
                binding.imageItem.setSliderAdapter(imageAutoAdapter)
                binding.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)

                binding.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                binding.imageItem.autoCycleDirection =
                    SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
                binding.imageItem.indicatorSelectedColor = Color.WHITE
                binding.imageItem.indicatorUnselectedColor = Color.GRAY
                binding.imageItem.scrollTimeInSec = 4
                binding.imageItem.startAutoCycle()

                binding.tvTenPhong.text = item.name
                binding.tvAmountBedRoom.text =
                    "${item.maxNguoiLon} người lớn, ${item.maxTreEm} trẻ em"
                binding.tvArea.text = "${item.dienTich} m²"
                binding.tvCountBathroom.text = item.bedroom[0].name
                binding.price.text = fm.format(item.price)
            }
        }
    }

    inner class ImageAutoSliderAdapter(private val listImage: List<String>) :
        SliderViewAdapter<ImageAutoSliderAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup): Holder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
            return Holder(view)
        }

        override fun onBindViewHolder(viewHolder: Holder, position: Int) {
            loadImage(viewHolder.imageView.context, listImage[position], viewHolder.imageView)
        }

        override fun getCount(): Int {
            return listImage.size
        }

        inner class Holder(itemView: View) : ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true
        if (ContextCompat.checkSelfPermission(
                requireActivity().applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            mMap.isMyLocationEnabled = true
        }
    }

    private fun showMakerAndText(hotel: Hotel) {
        if (::mMap.isInitialized) {
            val location = hotel.location.coordinates
            val myLocation = LatLng(location[1], location[0])

            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLocation, 13f)
            mMap.animateCamera(cameraUpdate)

            val cameraPosition =
                CameraPosition.Builder().target(myLocation).zoom(90f).bearing(0f).tilt(40f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            // Vẽ marker
            val markerOptions = MarkerOptions().position(myLocation).title(hotel.name)
                .snippet("${hotel.sonha}, ${hotel.xa}, ${hotel.huyen}, ${hotel.tinh}")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            mMap.addMarker(markerOptions)!!
        }
    }

}