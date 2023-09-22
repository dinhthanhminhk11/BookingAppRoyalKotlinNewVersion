package com.example.bookingapproyalkotlinver3.data.model.hotel

data class LocationNearByRequest(
    val longitude: Double, val latitude: Double, val dist: Int, private val treEm: Int = 0
)