package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.MySharedPreferences
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.data.util.view.checkLocationPermission
import com.example.bookingapproyalkotlinver3.data.util.view.requestLocationPermission
import com.example.bookingapproyalkotlinver3.data.util.view.setUnderlinedText
import com.example.bookingapproyalkotlinver3.databinding.BetweenViewHomeBinding
import com.example.bookingapproyalkotlinver3.databinding.BottomViewHomeBinding
import com.example.bookingapproyalkotlinver3.databinding.FragmentHomeBinding
import com.example.bookingapproyalkotlinver3.databinding.TopViewHomeBinding
import com.example.bookingapproyalkotlinver3.ui.adapter.BestForYouAdapter
import com.example.bookingapproyalkotlinver3.ui.adapter.NearFromYouAdapter
import com.example.bookingapproyalkotlinver3.ui.adapter.loading.ShimmerBestForYouAdapter
import com.example.bookingapproyalkotlinver3.ui.adapter.loading.ShimmerNearByFromYouAdapter
import com.example.bookingapproyalkotlinver3.ui.bottomsheet.BottomSheetPersonHome
import com.example.bookingapproyalkotlinver3.ui.dialog.DialogConfirmLogin
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    private var currentTimeNow: Date? = null
    private var currentTimeTomorrow: Date? = null
    private var daysDiffPrivate: Int = 1
    private var checkStartDate = "";
    private var checkEndDate = "";
    private var countRoom = 2
    private var countPerson = 2
    private var countChildren = 2
    private var ageChildren = 1
    private lateinit var materialDatePicker: MaterialDatePicker<Pair<Long, Long>>
    private lateinit var nearFromYouAdapter: NearFromYouAdapter
    private lateinit var homeViewAdapter: HomeViewAdapter
    private lateinit var bestForYouAdapter: BestForYouAdapter
    private var isDataLoaded = true
    private var isDataLoadedBestForYou = true
    private var positionListNearFrom = 0

    override fun initView() {
        initRecyclerView()

        val builder: MaterialDatePicker.Builder<Pair<Long, Long>> =
            MaterialDatePicker.Builder.dateRangePicker()

        val constraintBuilder: CalendarConstraints.Builder = CalendarConstraints.Builder()
        constraintBuilder.setValidator(DateValidatorPointForward.now())

        val constraintsBuilder: CalendarConstraints.Builder = CalendarConstraints.Builder()

        builder.setCalendarConstraints(constraintsBuilder.build())
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker)
        materialDatePicker = builder.setTitleText(getString(R.string.Select_a_date))
            .setPositiveButtonText(getString(R.string.SAVE))
            .setNegativeButtonText(getString(R.string.Thoat)).setSelection(
                androidx.core.util.Pair(
                    MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            ).setCalendarConstraints(constraintBuilder.build()).build()
    }

    override fun initOnClickListener() {

        nearFromYouAdapter.setOnItemClickListener { hotel, position ->
            val bundle = Bundle().apply {
                putSerializable(AppConstant.HOTEL_EXTRA, hotel._id)
                Log.e("Minh", hotel._id)
            }
            findNavController().navigate(
                R.id.action_kingMainFragment_to_detailHotelFragment, bundle
            )

            positionListNearFrom = position
        }

        bestForYouAdapter.setOnItemClickListener { hotel, _ ->
            val bundle = Bundle().apply {
                putSerializable(AppConstant.HOTEL_EXTRA, hotel._id)
            }
            findNavController().navigate(
                R.id.action_kingMainFragment_to_detailHotelFragment, bundle
            )
        }

        binding.bellMain.setOnClickListener {
            if (MySharedPreferences.getInstance(requireActivity())
                    .getString(AppConstant.TOKEN_USER, "").isNullOrEmpty()
            ) {
                DialogConfirmLogin.create(requireActivity()) {
                    findNavController().navigate(
                        R.id.action_kingMainFragment_to_loginFragment
                    )
                }.show()
            } else {
                findNavController().navigate(
                    R.id.action_kingMainFragment_to_notificationFragment
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeLiveData() {
        viewModel.ctyData.observe(viewLifecycleOwner) {
            binding.nameAddress.text = it.toString()
        }

        viewModel.resourceMutableLiveDataHotelNearBy.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {hotelResponse ->
                        isDataLoaded = false
                        nearFromYouAdapter.differ.submitList(hotelResponse.data.toList())
                        homeViewAdapter.notifyDataSetChanged()
                    }
                }

                is Resource.Error -> {
                    it.message?.let {
                        isDataLoaded = false
                    }
                }

                is Resource.Loading -> {
                    isDataLoaded = true
                }
            }
        }

        viewModel.listAllHotelMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        isDataLoadedBestForYou = false
                        bestForYouAdapter.differ.submitList(it.datapros.toList())
                        homeViewAdapter.notifyDataSetChanged()
                    }
                }

                is Resource.Error -> {
                    it.message?.let {
                        isDataLoadedBestForYou = false
                    }
                }

                is Resource.Loading -> {
                    isDataLoadedBestForYou = true
                }

                else -> {}
            }
        }

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkLocationPermission(activity!!)) {
            viewModel.getCurrentLocation(requireActivity())
        } else {
            requestLocationPermission(activity!!)
        }

        viewModel.getAllListHotel()
    }

    override fun initData() {

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        handlePermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initRecyclerView() {
        nearFromYouAdapter = NearFromYouAdapter()
        homeViewAdapter = HomeViewAdapter()
        bestForYouAdapter = BestForYouAdapter()
        binding.listViewHome.apply {
            adapter = homeViewAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }


    private fun handlePermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getCurrentLocation(requireActivity())
            } else {
                binding.nameAddress.visibility = View.GONE
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }

    inner class HomeViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val TOP_TYPE = 1
        private val BETWEEN_TYPE = 2
        private val BOTTOM_TYPE = 3
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TOP_TYPE -> {
                    val view = TopViewHomeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    TopViewHolder(view)
                }

                BETWEEN_TYPE -> {
                    val view = BetweenViewHomeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    BetweenViewHolder(view)
                }

                BOTTOM_TYPE -> {
                    val view = BottomViewHomeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    BottomViewHolder(view)
                }

                else -> throw IllegalArgumentException("Unknown view type")
            }
        }

        override fun getItemCount(): Int = 3

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                TOP_TYPE -> {
                    val topViewHolder = holder as TopViewHolder
                    topViewHolder.bind()
                }

                BETWEEN_TYPE -> {
                    val betweenViewHolder = holder as BetweenViewHolder
                    betweenViewHolder.bind()
                }

                BOTTOM_TYPE -> {
                    val bottomViewHolder = holder as BottomViewHolder
                    bottomViewHolder.bind()
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> TOP_TYPE
                1 -> BETWEEN_TYPE
                2 -> BOTTOM_TYPE
                else -> throw IllegalArgumentException("Unknown position: $position")
            }
        }

        inner class TopViewHolder(val binding: TopViewHomeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind() {
                initDate()
                initPerson()
                binding.contentDate.setOnClickListener {
                    if (!materialDatePicker.isAdded) {
                        materialDatePicker.show(
                            requireActivity().supportFragmentManager, "DATE_PICKER"
                        )
                    }

                    materialDatePicker.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onCreate(owner: LifecycleOwner) {}

                        override fun onStart(owner: LifecycleOwner) {
                            val root: View = materialDatePicker.requireView()
                        }

                        override fun onResume(owner: LifecycleOwner) {}

                        override fun onDestroy(owner: LifecycleOwner) {
                            materialDatePicker.lifecycle.removeObserver(this)
                        }
                    })

                    materialDatePicker.addOnPositiveButtonClickListener { selection ->
                        val startDate: Long? = selection.first
                        val endDate: Long? = selection.second

                        SimpleDateFormat("EEE", Locale.getDefault()).format(startDate).apply {
                            binding.tvTimeNhanPhong.text = this
                        }
                        SimpleDateFormat("EEE", Locale.getDefault()).format(endDate).apply {
                            binding.dayEnd.text = this
                        }

                        SimpleDateFormat("dd", Locale.getDefault()).format(startDate).apply {
                            binding.startDate.text = this
                        }
                        SimpleDateFormat("dd", Locale.getDefault()).format(endDate).apply {
                            binding.endDate.text = this
                        }

                        SimpleDateFormat("MM", Locale.getDefault()).format(startDate).apply {
                            binding.monthDate.text = "${getString(R.string.Month)} $this"
                        }
                        SimpleDateFormat("MM", Locale.getDefault()).format(endDate).apply {
                            binding.monthEnd.text = "${getString(R.string.Month)} $this"
                        }

                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startDate)
                            .apply {
                                checkStartDate = this
                            }
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(endDate).apply {
                            checkEndDate = this
                        }

                        if (startDate != null && endDate != null) {
                            val msDiff: Long = endDate - startDate
                            val daysDiff: Long = TimeUnit.MILLISECONDS.toDays(msDiff)

                            binding.payDay.text = daysDiff.toString()
                            daysDiffPrivate = daysDiff.toInt()
                        }
                    }
                }
                binding.contentPerson.setOnClickListener {
                    val bottomSheetPersonHome = BottomSheetPersonHome(requireActivity(),
                        object : BottomSheetPersonHome.Callback {
                            @SuppressLint("SetTextI18n")
                            override fun onCLickSum(
                                person: Int, children: Int, countRoom: Int, age: Int
                            ) {
                                binding.countRoom.text = "$countRoom ${getString(R.string.Room)}"
                                binding.countChildren.text =
                                    "$children ${getString(R.string.Children)}"
                                binding.countPerson.text = "$person ${getString(R.string.Adult)}"

                                countPerson = person;
                                countChildren = children;
                                this@HomeFragment.countRoom = countRoom;
                                ageChildren = age;
                            }
                        })
                    bottomSheetPersonHome.show()
                    bottomSheetPersonHome.setCanceledOnTouchOutside(false)
                }
            }

            @SuppressLint("SetTextI18n")
            private fun initDate() {
                binding.payDay.text = daysDiffPrivate.toString()

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                currentTimeNow = Calendar.getInstance().time
                currentTimeTomorrow = calendar.time

                currentTimeNow?.let {
                    SimpleDateFormat("EEE", Locale.getDefault()).format(it).apply {
                        binding.tvTimeNhanPhong.text = this
                    }
                }
                currentTimeTomorrow?.let {
                    SimpleDateFormat("EEE", Locale.getDefault()).format(it).apply {
                        binding.dayEnd.text = this
                    }
                }

                currentTimeNow?.let {
                    SimpleDateFormat("dd", Locale.getDefault()).format(it).apply {
                        binding.startDate.text = this
                    }
                }
                currentTimeTomorrow?.let {
                    SimpleDateFormat("dd", Locale.getDefault()).format(it).apply {
                        binding.endDate.text = this
                    }
                }

                currentTimeNow?.let {
                    SimpleDateFormat("MM", Locale.getDefault()).format(it).apply {
                        binding.monthDate.text = "${getString(R.string.Month)} $this"
                    }
                }
                currentTimeTomorrow?.let {
                    SimpleDateFormat("MM", Locale.getDefault()).format(it).apply {
                        binding.monthEnd.text = "${getString(R.string.Month)} $this"
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            private fun initPerson() {
                countRoom = MySharedPreferences.getInstance(requireActivity())
                    .getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, 2)
                countPerson = MySharedPreferences.getInstance(requireActivity())
                    .getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, 2)
                countChildren = MySharedPreferences.getInstance(requireActivity())
                    .getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, 2)
                ageChildren = MySharedPreferences.getInstance(requireActivity())
                    .getInt(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, 1)
                val textSearch = MySharedPreferences.getInstance(requireActivity()).getString(
                    AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH,
                    getString(R.string.Nearest_Hotels)
                )

                binding.countRoom.text = "$countRoom ${getString(R.string.Room)}"
                binding.countChildren.text = "$countChildren ${getString(R.string.Children)}"
                binding.countPerson.text = "$countPerson ${getString(R.string.Adult)}"
                binding.textSearch.text = textSearch
            }
        }

        inner class BetweenViewHolder(val binding: BetweenViewHomeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("NotifyDataSetChanged")
            fun bind() {
                binding.seeMoreNearFromYouHomeFragment.setUnderlinedText(binding.seeMoreNearFromYouHomeFragment.text.toString())

                binding.recyclerviewNearFromYouHomeFragment.apply {
                    layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    adapter = if (isDataLoaded) {
                        ShimmerNearByFromYouAdapter(2)
                    } else {
                        nearFromYouAdapter
                    }
                }.scrollToPosition(positionListNearFrom)

                binding.seeMoreNearFromYouHomeFragment.setOnClickListener {
                    findNavController().navigate(R.id.action_kingMainFragment_to_nearByFromYouFragment)
                }
            }
        }

        inner class BottomViewHolder(val binding: BottomViewHomeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind() {
                binding.seeMoreBestForYouHomeFragment.setUnderlinedText(binding.seeMoreBestForYouHomeFragment.text.toString())

                binding.recyclerviewBestForYouHomeFragment.apply {
                    layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = if (isDataLoadedBestForYou) {
                        ShimmerBestForYouAdapter(6)
                    } else {
                        bestForYouAdapter
                    }
                }
            }
        }
    }
}