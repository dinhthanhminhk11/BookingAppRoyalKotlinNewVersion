package com.example.bookingapproyalkotlinver3.data.util.view

import com.example.bookingapproyalkotlinver3.data.util.Resource
import retrofit2.Response


fun <T> responseToResource(response: Response<T>): Resource<T> {
    if (response.isSuccessful) {
        response.body()?.let {
            return Resource.Success(it)
        }
    }
    return Resource.Error(response.message())
}