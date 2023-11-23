package com.example.bookingapproyalkotlinver3.constant

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookingapproyalkotlinver3.R
import java.text.DecimalFormat

object AppConstant {
    const val BASE = "https://weathered-wind-3010.fly.dev"
    const val BASE_URL = "$BASE/api/"

    const val TYPE_LIGHT = 1;
    const val TYPE_DARK = 2;

    const val SHAREDPREFERENCES_USER_COUNT_PERSON = "SHAREDPREFERENCES_USER_COUNT_PERSON"
    const val SHAREDPREFERENCES_USER_COUNT_ROOM = "SHAREDPREFERENCES_USER_COUNT_ROOM"
    const val SHAREDPREFERENCES_USER_COUNT_CHILDREN = "SHAREDPREFERENCES_USER_COUNT_CHILDREN"
    const val SHAREDPREFERENCES_USER_TEXT_SEARCH = "SHAREDPREFERENCES_USER_TEXT_SEARCH"
    const val SHAREDPREFERENCES_USER_AGE_CHILDREN = "SHAREDPREFERENCES_USER_AGE_CHILDREN"
    const val SHAREDPREFERENCES_USER_START_PRICE = "SHAREDPREFERENCES_USER_START_PRICE"
    const val SHAREDPREFERENCES_USER_END_PRICE = "SHAREDPREFERENCES_USER_END_PRICE"
    const val SHAREDPREFERENCES_USER_STAR = "SHAREDPREFERENCES_USER_STAR"


    const val TAG_SETTING_TITLE = "TAG_SETTING_TITLE"
    const val TAG_SETTING_NOT_NULL_LOGIN = "TAG_SETTING_NOT_NULL_LOGIN"
    const val TAG_SETTING_SHOW_INFO = "TAG_SETTING_SHOW_INFO"
    const val TAG_SETTING_NOTIFICATION = "TAG_SETTING_NOTIFICATION"
    const val TAG_SETTING_PAYMENT = "TAG_SETTING_PAYMENT"
    const val TAG_SETTING_CHANG_PASS = "TAG_SETTING_CHANG_PASS"
    const val TAG_SETTING_CHANG_LANGUAGE = "TAG_SETTING_CHANG_LANGUAGE"
    const val TAG_SETTING_CHANG_THEME = "TAG_SETTING_CHANG_THEME"
    const val TAG_SETTING_CHANG_HELP = "TAG_SETTING_CHANG_HELP"
    const val TAG_SETTING_ABOUT_US = "TAG_SETTING_ABOUT_US"
    const val TAG_SETTING_LOGOUT = "TAG_SETTING_LOGOUT"

    const val HOTEL_EXTRA = "HOTEL_EXTRA"
    const val ROOM_EXTRA = "ROOM_EXTRA"
    const val CANCEL_BOOKING = "CANCEL_BOOKING"
    const val AGE_CHILDREN = "AGE_CHILDREN"
    const val TOKEN_USER = "TOKEN_USER"
    const val GALLERY_LIST = "galleryList"
    const val POSITION_GALLERY = "POSITION_GALLERY"
}