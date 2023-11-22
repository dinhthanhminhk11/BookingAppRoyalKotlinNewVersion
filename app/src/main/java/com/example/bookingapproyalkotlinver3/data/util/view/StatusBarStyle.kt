package com.example.bookingapproyalkotlinver3.data.util.view

import android.os.Build
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.bookingapproyalkotlinver3.constant.AppConstant

fun setStatusBarStyle(type: Int, statusBarColor: Int, activity: FragmentActivity) {
    activity.window.statusBarColor = statusBarColor
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = if (type == AppConstant.TYPE_LIGHT) {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            0
        }
    }
}