package com.example.bookingapproyalkotlinver3.data.repository.dataSourceImpl

import com.example.bookingapproyalkotlinver3.data.api.APIService
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.RemoteDataSource
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: APIService) : RemoteDataSource {
    override suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Response<HotelResponseNearBy> =
        apiService.getListHotelNearBy(locationNearByRequest)

    override suspend fun getAllListHotel(): Response<HotelResponse> = apiService.getAllListHotel()
}