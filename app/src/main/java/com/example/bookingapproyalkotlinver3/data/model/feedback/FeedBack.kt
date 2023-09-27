package com.example.bookingapproyalkotlinver3.data.model.feedback

data class FeedBack(
    val idHouse: String,
    val idUser: String,
    val imgUser: String,
    val name: String,
    val email: String,
    val sao: Int,
    val time: String,
    val textUser: String,
    val textHost: String
)