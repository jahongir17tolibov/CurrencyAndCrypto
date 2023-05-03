package com.jt17.currencycrypto.di

import com.jt17.currencycrypto.data.local.AppDao
import com.jt17.currencycrypto.data.remote.RemoteDataSource
import com.jt17.currencycrypto.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @[Provides Singleton]
    fun provideRepository(remoteDataSource: RemoteDataSource, appDao: AppDao): MainRepository =
        MainRepository(remoteDataSource, appDao)

}