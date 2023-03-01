package com.jt17.currencycrypto.di

import com.jt17.currencycrypto.networking.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CURRENCY_URL = "https://cbu.uz/uz/arkhiv-kursov-valyut/"
    private const val CRYPTO_URL = "https://api.coinlore.net/api/"

    @[Provides Singleton]
    fun provideJsonGsonConvertor(): GsonConverterFactory = GsonConverterFactory.create()

    //    provide currencies api
    @[Provides Singleton Named("ret1")]
    fun provideCurrencyRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(CURRENCY_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .build()

    //     provide for crypto api
    @[Provides Singleton Named("ret2")]
    fun provideCryptoRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(CRYPTO_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .build()

}