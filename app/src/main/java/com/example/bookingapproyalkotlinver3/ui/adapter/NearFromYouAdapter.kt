package com.example.bookingapproyalkotlinver3.ui.adapter

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.databinding.ItemNearfromyouHomefragmentBinding
import java.text.DecimalFormat

class NearFromYouAdapter : RecyclerView.Adapter<NearFromYouAdapter.ViewHolder>() {
    val df = DecimalFormat("0.0")

    private val callback = object : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNearfromyouHomefragmentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        val size = differ.currentList.size
        return if (size > 6) 6 else size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel: Hotel = differ.currentList[position]
        holder.bind(hotel, position)
    }

    inner class ViewHolder(val binding: ItemNearfromyouHomefragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Hotel, position: Int) {
            val options =
                RequestOptions().centerCrop().placeholder(R.drawable.imageloading)
                    .error(R.drawable.imageerror)

            Glide.with(binding.imgItemNearFromYou.context).load(item.images[0]).apply(options)
                .into(binding.imgItemNearFromYou)

            binding.tvAddressItemNearFromYou.text =
                "${item.sonha}, ${item.xa}, ${item.huyen}, ${item.tinh}"
            binding.tvDistanceItemNearFromYou.text = "${df.format(item.calculated / 1000)} Km"
            binding.tvNameItemNearFromYou.text = item.name

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item, position)
                }
            }
        }
    }

    private var onItemClickListener: ((Hotel, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Hotel, Int) -> Unit) {
        onItemClickListener = listener
    }
}