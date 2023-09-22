package com.example.bookingapproyalkotlinver3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookingapproyalkotlinver3.data.model.PostTest
import com.example.bookingapproyalkotlinver3.data.util.StringListConverter


@Database(
    entities = [PostTest::class], version = 1, exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class BookingDatabase : RoomDatabase(){
    abstract fun getBookingDao(): BookingDao
}