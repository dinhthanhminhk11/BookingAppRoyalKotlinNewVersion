package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.data.util.view.loadImage
import com.example.bookingapproyalkotlinver3.data.util.view.setStatusBarStyle
import com.example.bookingapproyalkotlinver3.databinding.FragmentDetailGalleryBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemDetailGalleryBinding


class DetailGalleryFragment :
    BaseFragment<FragmentDetailGalleryBinding>(FragmentDetailGalleryBinding::inflate) {
    private var galleryList: ArrayList<String> = ArrayList()
    private var position: Int = 0
    private var clicked: Boolean = true;

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.imgCancelGallery.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.viewPageGallery.apply {
            adapter = DetailGalleryAdapter(galleryList)
            clipToPadding = true
        }
        binding.viewPageGallery.addItemDecoration(
            DividerItemDecoration(
                requireActivity(), LinearLayoutManager.HORIZONTAL
            )
        )
        binding.viewPageGallery.setCurrentItem(position, true)

        binding.viewPageGallery.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvNumberChange.text = (position + 1).toString()
            }
        })

        binding.tvCountGallery.text = "/ ${galleryList.size}"
        binding.tvNumberChange.text = (position + 1).toString()
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            galleryList = bundle.getStringArrayList(AppConstant.GALLERY_LIST) ?: ArrayList()
            position = bundle.getInt(AppConstant.POSITION_GALLERY)
        }
    }

    override fun initData() {

    }


    inner class DetailGalleryAdapter(private val list: ArrayList<String>) :
        RecyclerView.Adapter<DetailGalleryAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                ItemDetailGalleryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(position)
            holder.itemView.setOnClickListener {
                clicked = !clicked
                updateUIState()
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class MyViewHolder(val binding: ItemDetailGalleryBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int) {
                loadImage(itemView.context, list[position], binding.imgDetailGallery)
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun updateUIState() {
        if (clicked) {
            binding.constraintDetailImage.setBackgroundResource(R.color.white)
            binding.imgCancelGallery.visibility = View.VISIBLE
            binding.view.visibility = View.VISIBLE
            binding.viewPageGallery.isUserInputEnabled = true
            setStatusBarStyle(AppConstant.TYPE_DARK, activity!!.getColor(R.color.black), activity!!)
        } else {
            binding.constraintDetailImage.setBackgroundResource(R.color.black)
            binding.imgCancelGallery.visibility = View.GONE
            binding.viewPageGallery.isUserInputEnabled = false
            binding.view.visibility = View.GONE
            setStatusBarStyle(
                AppConstant.TYPE_LIGHT,
                activity!!.getColor(R.color.white),
                activity!!
            )

        }
    }
}