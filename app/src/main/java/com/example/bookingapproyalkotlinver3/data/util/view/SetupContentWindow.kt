package com.example.bookingapproyalkotlinver3.data.util.view

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity

fun setupContentWindow(activity: FragmentActivity) {
    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    activity.window.statusBarColor = Color.TRANSPARENT
    activity.window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}