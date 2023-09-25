package com.example.bookingapproyalkotlinver3.data.repository

import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.model.notification.NotiResponse
import com.example.bookingapproyalkotlinver3.data.model.user.LoginResponse
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import com.example.bookingapproyalkotlinver3.data.util.Resource

interface Repository {
    suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Resource<HotelResponseNearBy>
    suspend fun getAllListHotel(): Resource<HotelResponse>
    suspend fun getUser(userLogin: UserLogin): Resource<LoginResponse>
    suspend fun getUserByToken(token: String): Resource<LoginResponse>
    suspend fun getListNotification(id: String): Resource<NotiResponse>
}