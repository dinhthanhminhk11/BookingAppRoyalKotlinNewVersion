package com.example.bookingapproyalkotlinver3.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.data.model.hotel.Convenient
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.notification.Notification
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentDetailHotelActivityBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemConvenientBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemGalleryBinding
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailHotelFragment : BaseViewModelFragment<FragmentDetailHotelActivityBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private var isClickSpeed: Boolean = false
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var convenientAdapter: ConvenientAdapter
    override fun initView() {
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
        }
    }

    override fun observeLiveData() {
        viewModel.hotelResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
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

}