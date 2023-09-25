package com.example.bookingapproyalkotlinver3.data.model

import com.google.gson.annotations.SerializedName

data class TextResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)