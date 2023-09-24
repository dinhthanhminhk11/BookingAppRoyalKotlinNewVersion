package com.example.bookingapproyalkotlinver3.data.model.user

import com.google.gson.annotations.SerializedName

data class User(
    private val _id: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("countBooking")
    val countBooking: Int,
    @SerializedName("tokenDevice")
    val tokenDevice: String,
    @SerializedName("checkAccount")
    val checkAccount: Boolean
)