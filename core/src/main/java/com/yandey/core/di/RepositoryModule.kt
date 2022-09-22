package com.yandey.core.di

import com.yandey.core.data.UserRepositoryImpl
import com.yandey.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [RemoteDataModule::class, LocalDataModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(userRepositoryImpl: UserRepositoryImpl): IUserRepository
}