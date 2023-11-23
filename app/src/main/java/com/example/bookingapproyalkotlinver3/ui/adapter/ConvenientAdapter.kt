package com.example.bookingapproyalkotlinver3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.data.model.hotel.Convenient
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.data.util.view.loadImage
import com.example.bookingapproyalkotlinver3.databinding.ItemConvenientBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemNearfromyouHomefragmentBinding

class ConvenientAdapter(private val data: List<Convenient>) :
    RecyclerView.Adapter<ConvenientAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemConvenientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(convenient: Convenient) {
            loadImage(binding.image.context, convenient.iconImage, binding.image)
            binding.name.text = convenient.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConvenientAdapter.ViewHolder {
        return ViewHolder(
            ItemConvenientBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ConvenientAdapter.ViewHolder, position: Int) {
        val convenient: Convenient = data[position]
        holder.bind(convenient)
    }

    override fun getItemCount(): Int = data.size
}