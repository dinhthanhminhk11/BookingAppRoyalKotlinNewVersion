package com.example.bookingapproyalkotlinver3.data.util.view

import androidx.fragment.app.FragmentActivity
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.ui.customview.toast.CookieBar

fun showToastSuccess(activity: FragmentActivity, title: String, content: String) {
    CookieBar.build(activity)
        .setTitle(title)
        .setMessage(content)
        .setIcon(R.drawable.ic_complete_order).setTitleColor(R.color.black)
        .setMessageColor(R.color.black).setDuration(3000)
        .setBackgroundRes(R.drawable.background_toast)
        .setCookiePosition(CookieBar.BOTTOM).show()
}

fun showToastError(activity: FragmentActivity?, title: String, content: String) {
    CookieBar.build(activity)
        .setTitle(title)
        .setMessage(content)
        .setIcon(R.drawable.ic_warning_icon_check).setTitleColor(R.color.black)
        .setMessageColor(R.color.black).setDuration(3000)
        .setBackgroundRes(R.drawable.background_toast)
        .setCookiePosition(CookieBar.BOTTOM).show()
}