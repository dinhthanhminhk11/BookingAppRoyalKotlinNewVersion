package com.example.bookingapproyalkotlinver3.data.api

import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("hotelNearBy")
    suspend fun getListHotelNearBy(@Body locationNearByRequest: LocationNearByRequest): Response<HotelResponseNearBy>

    @GET("getAllHotelConfirm")
    suspend fun getAllListHotel(): Response<HotelResponse>
}