package com.example.bookingapproyalkotlinver3.data.model.user

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val user: User,
    @SerializedName("accessToken")
    val token: String
)





