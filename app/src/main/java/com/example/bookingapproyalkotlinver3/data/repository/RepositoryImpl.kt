package com.example.bookingapproyalkotlinver3.data.repository

import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.LocalDataSource
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.RemoteDataSource
import com.example.bookingapproyalkotlinver3.data.util.Resource
import retrofit2.Response

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {
    override suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Resource<HotelResponseNearBy> =
        responseToResource(remoteDataSource.getListHotelNearBy(locationNearByRequest))

    override suspend fun getAllListHotel(): Resource<HotelResponse> =
        responseToResource(remoteDataSource.getAllListHotel())


    private fun <T> responseToResource(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}