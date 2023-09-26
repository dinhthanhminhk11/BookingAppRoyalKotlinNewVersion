package com.example.bookingapproyalkotlinver3.data.model.hotel

import com.example.bookingapproyalkotlinver3.data.model.user.User
import com.google.gson.annotations.SerializedName

data class HotelById(
    @SerializedName("dataHotel")
    val dataHotel: Hotel,
    @SerializedName("dataRoom")
    val dataRoom: ArrayList<Room>,
    @SerializedName("dataUser")
    val dataUser: User
)