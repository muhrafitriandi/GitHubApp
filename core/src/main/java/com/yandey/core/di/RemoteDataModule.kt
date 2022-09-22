package com.yandey.core.di

import com.yandey.core.data.source.remote.network.ApiConfig
import com.yandey.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Provides
    fun provideApiService(): ApiService = ApiConfig.getApiService()
}