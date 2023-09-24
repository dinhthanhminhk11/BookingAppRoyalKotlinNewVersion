package com.example.bookingapproyalkotlinver3.constant

import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView

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

    const val HOTEL_EXTRA = "HOTEL_EXTRA"
    const val TOKEN_USER = "TOKEN_USER"
}

fun TextView.setUnderlinedText(text: CharSequence) {
    val spannableString = SpannableString(text)
    spannableString.setSpan(UnderlineSpan(), 0, text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}