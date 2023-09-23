package com.example.bookingapproyalkotlinver3.ui.adapter.loading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerNearByFromYouAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<ShimmerNearByFromYouAdapter.ShimmerViewHolder>() {
    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.layout_shimmer_near_by_hotel_item, parent, false)
        return ShimmerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        val shimmerLayout = holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        shimmerLayout.startShimmer()
    }

    override fun getItemCount(): Int = itemCount
}