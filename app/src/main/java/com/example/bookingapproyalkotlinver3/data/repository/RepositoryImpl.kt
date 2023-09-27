package com.example.bookingapproyalkotlinver3.data.repository

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

    override suspend fun getUser(userLogin: UserLogin): Resource<LoginResponse> =
        responseToResource(remoteDataSource.getUser(userLogin))

    override suspend fun getUserByToken(token: String): Resource<LoginResponse> =
        responseToResource(remoteDataSource.getUserByToken(token))

    override suspend fun getListNotification(id: String): Resource<NotiResponse> =
        responseToResource(remoteDataSource.getListNotification(id))

    override suspend fun updateNotificationSeen(id: String): Resource<TextResponse> =
        responseToResource(remoteDataSource.updateNotificationSeen(id))

    override suspend fun getListBookmarkByUser(id: String): Resource<BookmarkResponse> =
        responseToResource(remoteDataSource.getListBookmarkByUser(id))

    override suspend fun getHotelById(id: String): Resource<HotelById> =
        responseToResource(remoteDataSource.getHotelById(id))

    override suspend fun getFeedBack(idHouse: String): Resource<DataFeedBack> =
        responseToResource(remoteDataSource.getFeedBack(idHouse))

    override suspend fun getBookmarkByIdUserAndIdHouse(
        idUser: String,
        idHotel: String
    ): Resource<BookmarkResponse> =
        responseToResource(remoteDataSource.getBookmarkByIdUserAndIdHouse(idUser, idHotel))


    private fun <T> responseToResource(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}