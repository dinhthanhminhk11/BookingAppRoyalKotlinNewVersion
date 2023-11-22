package com.example.bookingapproyalkotlinver3.data.util.view

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView

fun TextView.setBoldAndColorText(fullText: String, subText: String, color: Int) {
    val spannable = SpannableString(fullText)
    val startIndex = fullText.indexOf(subText)
    if (startIndex != -1) {
        spannable.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + subText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            startIndex + subText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    text = spannable
}