package com.example.bookingapproyalkotlinver3.data.util.view

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookingapproyalkotlinver3.R

fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
    val options = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.imageloading)
        .error(R.drawable.imageerror)

    Glide.with(context)
        .load(imageUrl)
        .apply(options)
        .into(imageView)
}