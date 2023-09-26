package com.example.bookingapproyalkotlinver3.data.model.hotel

import com.google.gson.annotations.SerializedName

data class Room(
    val _id: String,
    val idHotel: String,
    val name: String,
    val images: ArrayList<String>,
    val price: Int,
    val dienTich: String,
    val timeDat: String,
    val timeTra: String,
    @SerializedName("TienNghiPhong")
    val tienNghiPhong: ArrayList<Convenient>,
    val bedroom: ArrayList<Bedroom>,
    @SerializedName("MaxNguoiLon")
    val maxNguoiLon: Int,
    @SerializedName("MaxTreEm")
    val maxTreEm: Int,
    @SerializedName("SoPhong")
    val soPhong: Int,
    val mota: String,
    val __v: Int
)