package com.example.bookingapproyalkotlinver3.ui.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.data.model.bookmark.BookmarkResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentDetailHotelActivityBinding
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailHotelFragment : BaseViewModelFragment<FragmentDetailHotelActivityBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private var isClickSpeed: Boolean = true
    private lateinit var menuItem: MenuItem
    override fun initView() {
        initToolbar()
        binding.showMore.setUnderlinedText(binding.showMore.text.toString())
        binding.showMedical.setUnderlinedText(binding.showMedical.text.toString())
        binding.btnShowFeedback.setUnderlinedText(binding.btnShowFeedback.text.toString())
    }

    private fun initToolbar() {
        binding.toolBar.background = null
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        binding.toolBar.title = ""
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {
        viewModel.hotelResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        loadData(it)
                    }
                }

                is Resource.Loading -> {
                    it.message?.let {
                        showSnackbar(requireView(), it)
                    }
                }

                is Resource.Error -> {

                }
            }
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
    }

    override fun initData() {
        arguments?.let {
            viewModel.getHotelById(it.getString(AppConstant.HOTEL_EXTRA, ""))
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDetailHotelActivityBinding =
        FragmentDetailHotelActivityBinding.inflate(inflater, container, false)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iconbookmak -> {
                isClickSpeed = if (isClickSpeed) {
                    item.setIcon(R.drawable.ic_baseline_bookmark_24_white_full)
                    false
                } else {
                    item.setIcon(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar)
                    true
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.itemboomak, menu)
        menuItem = menu.findItem(R.id.iconbookmak)
        if (isClickSpeed) {
            menuItem.setIcon(R.drawable.ic_baseline_bookmark_24_white_full)
        } else {
            menuItem.setIcon(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar)
        }
    }

}