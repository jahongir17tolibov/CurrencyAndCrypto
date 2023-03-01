package com.jt17.currencycrypto.di

import android.content.Context
import androidx.room.Room
import com.jt17.currencycrypto.data.local.AppDao
import com.jt17.currencycrypto.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @[Provides Singleton]
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "CurrAndCry.db"
        ).build()

    @Provides
    fun provideAppDao(appDatabase: AppDatabase): AppDao = appDatabase.userDao()



}