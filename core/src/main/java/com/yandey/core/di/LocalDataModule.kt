package com.yandey.core.di

import android.content.Context
import androidx.room.Room
import com.yandey.core.data.source.local.room.UserDao
import com.yandey.core.data.source.local.room.UserDatabase
import com.yandey.core.utils.Constants.USER_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            USER_DB
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao =
        userDatabase.userDao()
}