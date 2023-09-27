package com.example.bookingapproyalkotlinver3.data.repository.dataSourceImpl

import com.example.bookingapproyalkotlinver3.data.api.APIService
import com.example.bookingapproyalkotlinver3.data.model.TextResponse
import com.example.bookingapproyalkotlinver3.data.model.bookmark.BookmarkResponse
import com.example.bookingapproyalkotlinver3.data.model.feedback.DataFeedBack
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.model.notification.NotiResponse
import com.example.bookingapproyalkotlinver3.data.model.user.LoginResponse
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.RemoteDataSource
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: APIService) : RemoteDataSource {
    override suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Response<HotelResponseNearBy> =
        apiService.getListHotelNearBy(locationNearByRequest)

    override suspend fun getAllListHotel(): Response<HotelResponse> = apiService.getAllListHotel()
    override suspend fun getUser(userLogin: UserLogin): Response<LoginResponse> =
        apiService.getUser(userLogin)

    override suspend fun getUserByToken(token: String): Response<LoginResponse> =
        apiService.getUserByToken(token)

    override suspend fun getListNotification(id: String): Response<NotiResponse> =
        apiService.getListNotification(id)

    override suspend fun updateNotificationSeen(id: String): Response<TextResponse> =
        apiService.updateNotificationSeen(id)

    override suspend fun getListBookmarkByUser(id: String): Response<BookmarkResponse> =
        apiService.getListBookmarkByUser(id)

    override suspend fun getHotelById(id: String): Response<HotelById> =
        apiService.getHotelById(id)

    override suspend fun getFeedBack(idHouse: String): Response<DataFeedBack> =
        apiService.getFeedBack(idHouse)

    override suspend fun getBookmarkByIdUserAndIdHouse(
        idUser: String,
        idHotel: String
    ): Response<BookmarkResponse> = apiService.getBookmarkByIdUserAndIdHouse(idUser, idHotel)
}