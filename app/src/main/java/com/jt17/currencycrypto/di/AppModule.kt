package com.jt17.currencycrypto.di

import android.app.Application
import com.jt17.currencycrypto.db.AppDao
import com.jt17.currencycrypto.db.AppDatabase
import com.jt17.currencycrypto.di.AppModule_ProvideJsonGsonConvertorFactory.provideJsonGsonConvertor
import com.jt17.currencycrypto.networking.ApiService
import com.jt17.currencycrypto.repository.MainRepository
import com.jt17.currencycrypto.viewmodel.CurrencyViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private const val CURRENCY_URL = "https://cbu.uz/uz/arkhiv-kursov-valyut/"
        private const val CRYPTO_URL = "https://api.coinlore.net/api/"
    }

    @[Provides Singleton]
    fun getAppDatabase(context: Application): AppDatabase = AppDatabase.getDatabaseClient(context)

    @[Provides Singleton]
    fun getAppDao(appDatabase: AppDatabase): AppDao = appDatabase.userDao()

    @[Provides Singleton]
    fun provideJsonGsonConvertor(): GsonConverterFactory = GsonConverterFactory.create()

//    @[Provides Singleton]
//    fun provideCurrencyApi(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//
//    @[Provides Singleton]
//    fun provideCryptoApi(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }

    //     provide for currencies api
    @[Provides Singleton]
    @Named("api1")
    fun provideCurrencyRetrofit(): ApiService = Retrofit.Builder()
        .baseUrl(CURRENCY_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .build()
        .create(ApiService::class.java)

    //     provide for crypto api
    @[Provides Singleton]
    @Named("api2")
    fun provideCryptoRetrofit(): ApiService = Retrofit.Builder()
        .baseUrl(CRYPTO_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .build()
        .create(ApiService::class.java)

//    @Provides
//    fun mainRepo(apiService1: ApiService): MainRepository {
//        return MainRepository(apiService1)
//    }

}