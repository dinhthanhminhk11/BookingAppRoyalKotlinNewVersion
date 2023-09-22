package com.example.bookingapproyalkotlinver3.di

import com.example.bookingapproyalkotlinver3.data.db.BookingDao
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.LocalDataSource
import com.example.bookingapproyalkotlinver3.data.repository.dataSourceImpl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(bookingDao: BookingDao): LocalDataSource {
        return LocalDataSourceImpl(bookingDao)
    }
}