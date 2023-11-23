package com.example.bookingapproyalkotlinver3.data.repository.dataSource

import com.example.bookingapproyalkotlinver3.data.model.TextResponse
import com.example.bookingapproyalkotlinver3.data.model.bookmark.BookmarkResponse
import com.example.bookingapproyalkotlinver3.data.model.bookmark.PostIDUserAndIdHouse
import com.example.bookingapproyalkotlinver3.data.model.feedback.DataFeedBack
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.model.hotel.Room
import com.example.bookingapproyalkotlinver3.data.model.notification.NotiResponse
import com.example.bookingapproyalkotlinver3.data.model.user.LoginResponse
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteDataSource {
    suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Response<HotelResponseNearBy>
    suspend fun getAllListHotel(): Response<HotelResponse>
    suspend fun getUser(userLogin: UserLogin): Response<LoginResponse>
    suspend fun getUserByToken(token: String): Response<LoginResponse>
    suspend fun getListNotification(id: String): Response<NotiResponse>
    suspend fun updateNotificationSeen(id: String): Response<TextResponse>
    suspend fun getListBookmarkByUser(id: String): Response<BookmarkResponse>
    suspend fun getHotelById(id: String): Response<HotelById>
    suspend fun getFeedBack(idHouse: String): Response<DataFeedBack>
    suspend fun getBookmarkByIdUserAndIdHouse(
        idUser: String, idHotel: String
    ): Response<BookmarkResponse>

    suspend fun addBookmark(postIDUserAndIdHouse: PostIDUserAndIdHouse): Response<BookmarkResponse>
    suspend fun deleteBookmark(
        idUser: String, idHotel: String
    ): Response<BookmarkResponse>

    suspend fun getRoomById( id: String) : Response<Room>
}