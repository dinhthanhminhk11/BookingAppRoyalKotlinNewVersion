package com.example.bookingapproyalkotlinver3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.databinding.ItemBestforyouHomefragmentBinding

class BestForYouAdapter : RecyclerView.Adapter<BestForYouAdapter.ViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestForYouAdapter.ViewHolder {
        val binding = ItemBestforyouHomefragmentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemBestforyouHomefragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Hotel, position: Int) {
            val options =
                RequestOptions().centerCrop().placeholder(R.drawable.imageloading)
                    .error(R.drawable.imageerror)

            Glide.with(binding.imgItemBestForYou.context).load(item.images[0]).apply(options)
                .into(binding.imgItemBestForYou)
            binding.tvNameHouseItemBestforyou.text= item.name
            binding.tvPriceHouseItemBestforyou.text = item.giaDaoDong

            Glide.with(binding.imgItemBestForYou.context).load(item.convenient[0].iconImage).apply(options)
                .into(binding.icon1)

            Glide.with(binding.imgItemBestForYou.context).load(item.convenient[1].iconImage).apply(options)
                .into(binding.icon2)

            binding.nameIcon1.text = item.convenient[0].name
            binding.tvNameIcon1.text = item.convenient[1].name

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item, position)
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: BestForYouAdapter.ViewHolder, position: Int) {
        val hotel: Hotel = differ.currentList[position]
        holder.bind(hotel, position)
    }

    private var onItemClickListener: ((Hotel, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Hotel, Int) -> Unit) {
        onItemClickListener = listener
    }

}