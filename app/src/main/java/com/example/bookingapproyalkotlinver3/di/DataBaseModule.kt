package com.example.bookingapproyalkotlinver3.di

import android.app.Application
import androidx.room.Room
import com.example.bookingapproyalkotlinver3.data.db.BookingDao
import com.example.bookingapproyalkotlinver3.data.db.BookingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun providePostDatabase(app: Application): BookingDatabase {
        return Room.databaseBuilder(app, BookingDatabase::class.java, "find_a_room_db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providePostDao(postDatabase: BookingDatabase): BookingDao {
        return postDatabase.getBookingDao()
    }
}