package com.example.bookingapproyalkotlinver3.data.model

import com.example.bookingapproyalkotlinver3.R

data class SettingItem(
    val viewType: Int,
    val background: Int,
    val iconResourceLeft: Int,
    val iconResourceRight: Int = R.drawable.ic_button_infor_setting,
    val title: String,
    val description: String,
    var isVisible: Boolean = true
)