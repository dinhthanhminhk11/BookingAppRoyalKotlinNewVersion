package com.example.bookingapproyalkotlinver3.data.model.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(
    @SerializedName("message")
    val message: Boolean,
    @SerializedName("data")
    val data: List<Bookmark>
)