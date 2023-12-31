package com.example.bookingapproyalkotlinver3.data.api

import com.example.bookingapproyalkotlinver3.data.model.TextResponse
import com.example.bookingapproyalkotlinver3.data.model.bookmark.BookmarkResponse
import com.example.bookingapproyalkotlinver3.data.model.bookmark.PostIDUserAndIdHouse
import com.example.bookingapproyalkotlinver3.data.model.feedback.DataFeedBack
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelById
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponse
import com.example.bookingapproyalkotlinver3.data.model.hotel.HotelResponseNearBy
import com.example.bookingapproyalkotlinver3.data.model.hotel.LocationNearByRequest
import com.example.bookingapproyalkotlinver3.data.model.notification.NotiResponse
import com.example.bookingapproyalkotlinver3.data.model.user.LoginResponse
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @POST("hotelNearBy")
    suspend fun getListHotelNearBy(@Body locationNearByRequest: LocationNearByRequest): Response<HotelResponseNearBy>

    @GET("getAllHotelConfirm")
    suspend fun getAllListHotel(): Response<HotelResponse>

    @POST("signin")
    suspend fun getUser(@Body userLogin: UserLogin): Response<LoginResponse>

    @GET("getUserByToken")
    suspend fun getUserByToken(@Header("x-access-token") token: String): Response<LoginResponse>

    @GET("listNotificationByUser/{id}")
    suspend fun getListNotification(@Path("id") id: String): Response<NotiResponse>

    @PATCH("updateNotiSeen/{id}")
    suspend fun updateNotificationSeen(@Path("id") id: String): Response<TextResponse>

    @GET("listBookmarkById/{id}")
    suspend fun getListBookmarkByUser(@Path("id") id: String): Response<BookmarkResponse>

    @GET("getHotelById/{id}")
    suspend fun getHotelById(@Path("id") id: String): Response<HotelById>

    @GET("listFeedBack/{idHouse}")
    suspend fun getFeedBack(@Path("idHouse") idHouse: String): Response<DataFeedBack>

    @GET("getBookmarkByIdUserAndIdHouse/{id}/{idHotel}")
    suspend fun getBookmarkByIdUserAndIdHouse(
        @Path("id") idUser: String, @Path("idHotel") idHotel: String
    ): Response<BookmarkResponse>

    @POST("createBookmark")
    suspend fun addBookmark(@Body postIDUserAndIdHouse: PostIDUserAndIdHouse): Response<BookmarkResponse>

    @DELETE("deleteBookmark/{id}/{idHotel}")
    suspend fun deleteBookmark(
        @Path("id") idUser: String,
        @Path("idHotel") idHotel: String
    ): Response<BookmarkResponse>
}