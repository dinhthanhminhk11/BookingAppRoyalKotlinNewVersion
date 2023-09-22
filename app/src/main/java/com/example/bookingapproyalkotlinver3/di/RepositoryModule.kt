package com.example.bookingapproyalkotlinver3.di

import com.example.bookingapproyalkotlinver3.data.repository.Repository
import com.example.bookingapproyalkotlinver3.data.repository.RepositoryImpl
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.LocalDataSource
import com.example.bookingapproyalkotlinver3.data.repository.dataSource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return  RepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }
}