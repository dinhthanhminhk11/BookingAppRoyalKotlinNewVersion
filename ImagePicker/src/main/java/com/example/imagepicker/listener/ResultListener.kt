package com.example.imagepicker.listener

internal interface ResultListener<T> {
    fun onResult(t: T?)
}