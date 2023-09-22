package com.example.bookingapproyalkotlinver3.data.repository.dataSource

import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Response<HotelResponseNearBy>
    suspend fun getAllListHotel(): Response<HotelResponse>
}