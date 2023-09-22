package com.example.bookingapproyalkotlinver3.data.repository.dataSourceImpl

import com.example.bookingapproyalkotlinver3.data.db.BookingDao
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.LocalDataSource

class LocalDataSourceImpl(private val bookingDao: BookingDao) : LocalDataSource {
}