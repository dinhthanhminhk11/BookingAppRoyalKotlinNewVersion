package com.example.bookingapproyalkotlinver3.data.model.notification

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("_id")
    val id: String,
    val idOder: String,
    val idUser: String,
    val title: String,
    val content: String,
    val imageHoust: String,
    val date: String,
    val time: String,
    val isSeem: Boolean,
    val __v: Int
)