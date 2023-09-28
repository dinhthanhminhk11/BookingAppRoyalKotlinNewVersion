package com.example.bookingapproyalkotlinver3.data.model.hotel

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("type")
    val type: String,
    @SerializedName("coordinates")
    val coordinates: List<Double>
)