package com.example.bookingapproyalkotlinver3.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object TimeUtils {
    fun getTimeAgo(time: Long): String {
        val cal = Calendar.getInstance()
        val tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        cal.timeZone = tz
        cal.timeInMillis = time
        val now = Calendar.getInstance(tz)
        val dateFormat = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())
        dateFormat.timeZone = tz
        var timeString = dateFormat.format(cal.time)
        if (now[Calendar.DATE] == cal[Calendar.DATE]) {
            val diff = now.timeInMillis - cal.timeInMillis
            val hours = (diff / (60 * 60 * 1000)).toInt()
            timeString = if (hours > 0) {
                "$hours giờ trước"
            } else {
                val minutes = (diff / (60 * 1000)).toInt()
                if (minutes > 0) {
                    "$minutes phút trước"
                } else {
                    "vừa xong"
                }
            }
        } else if (now[Calendar.DATE] - cal[Calendar.DATE] == 1) {
            timeString = "Hôm qua"
        }
        return timeString
    }
}