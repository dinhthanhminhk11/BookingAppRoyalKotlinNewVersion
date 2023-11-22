package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.data.util.view.loadImage
import com.example.bookingapproyalkotlinver3.databinding.FragmentDetailImageHotelBinding

class DetailImageHotelFragment :
    BaseFragment<FragmentDetailImageHotelBinding>(FragmentDetailImageHotelBinding::inflate) {
    private var galleryList: ArrayList<String> = ArrayList()
    override fun initView() {
        binding.toolBar.title = getString(R.string.Gallery)
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 0) 2 else 1
            }
        }

        binding.rcvImageGallery.apply {
            layoutManager = gridLayoutManager
            adapter = GalleryImageAdapter(galleryList)
        }
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            galleryList = bundle.getStringArrayList(AppConstant.GALLERY_LIST) ?: ArrayList()
        }
    }

    override fun initData() {

    }

    inner class GalleryImageAdapter(private val image: ArrayList<String>) :
        RecyclerView.Adapter<GalleryImageAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutResId =
                if (viewType == 2) R.layout.item_gallery_second else R.layout.item_gallery_first
            val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            loadImage(holder.itemView.context, image[position], holder.imageView)
            holder.imageView.setOnClickListener {
                val bundle = Bundle()
                bundle.putStringArrayList(AppConstant.GALLERY_LIST, ArrayList(galleryList))
                bundle.putInt(AppConstant.POSITION_GALLERY, position)
                findNavController().navigate(
                    R.id.action_detailImageHotelFragment_to_detailGalleryFragment,
                    bundle
                )
            }
        }

        override fun getItemCount(): Int {
            return image.size
        }

        override fun getItemViewType(position: Int): Int {
            return if (position % 3 == 0) 2 else 1
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.img1)
        }
    }
}