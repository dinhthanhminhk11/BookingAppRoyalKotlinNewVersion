package com.example.bookingapproyalkotlinver3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.data.util.view.loadImage
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
        parent: ViewGroup, viewType: Int
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
            loadImage(binding.imgItemBestForYou.context, item.images[0], binding.imgItemBestForYou)
            loadImage(binding.icon1.context, item.convenient[0].iconImage, binding.icon1)
            loadImage(binding.icon2.context, item.convenient[1].iconImage, binding.icon2)

            binding.tvNameHouseItemBestforyou.text = item.name
            binding.tvPriceHouseItemBestforyou.text = item.giaDaoDong

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