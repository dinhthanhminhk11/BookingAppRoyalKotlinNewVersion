package com.example.bookingapproyalkotlinver3.data.model.bookmark

import com.google.gson.annotations.SerializedName

data class PostIDUserAndIdHouse(
    @SerializedName("idUser")
    val idUser: String,
    @SerializedName("idHotel")
    val idHotel: String
)