package com.example.bookingapproyalkotlinver3.data.repository

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
import com.example.bookingapproyalkotlinver3.data.util.Resource

interface Repository {
    suspend fun getListHotelNearBy(locationNearByRequest: LocationNearByRequest): Resource<HotelResponseNearBy>
    suspend fun getAllListHotel(): Resource<HotelResponse>
    suspend fun getUser(userLogin: UserLogin): Resource<LoginResponse>
    suspend fun getUserByToken(token: String): Resource<LoginResponse>
    suspend fun getListNotification(id: String): Resource<NotiResponse>
    suspend fun updateNotificationSeen(id: String): Resource<TextResponse>
    suspend fun getListBookmarkByUser(id: String): Resource<BookmarkResponse>
    suspend fun getHotelById(id: String): Resource<HotelById>
    suspend fun getFeedBack(idHouse: String): Resource<DataFeedBack>
    suspend fun getBookmarkByIdUserAndIdHouse(
        idUser: String, idHotel: String
    ): Resource<BookmarkResponse>

    suspend fun addBookmark(postIDUserAndIdHouse: PostIDUserAndIdHouse): Resource<BookmarkResponse>
    suspend fun deleteBookmark(
        idUser: String,
        idHotel: String
    ): Resource<BookmarkResponse>

    suspend fun getRoomById( id: String) : Resource<Room>
}