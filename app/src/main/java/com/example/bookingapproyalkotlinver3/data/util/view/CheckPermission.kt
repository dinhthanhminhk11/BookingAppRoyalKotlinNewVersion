package com.example.bookingapproyalkotlinver3.data.util.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.bookingapproyalkotlinver3.ui.fragment.home.HomeFragment

@SuppressLint("UseRequireInsteadOfGet")
fun checkLocationPermission(activity: FragmentActivity): Boolean {
    val permissionResult = ContextCompat.checkSelfPermission(
        activity, Manifest.permission.ACCESS_FINE_LOCATION
    )
    return permissionResult == PackageManager.PERMISSION_GRANTED
}

fun requestLocationPermission(activity: FragmentActivity) {
    activity.requestPermissions(
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        HomeFragment.LOCATION_PERMISSION_REQUEST_CODE
    )
}