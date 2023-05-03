package com.jt17.currencycrypto.di

import com.jt17.currencycrypto.utils.Constants.CRYPTO_URL
import com.jt17.currencycrypto.utils.Constants.CRY_TXT
import com.jt17.currencycrypto.utils.Constants.CURRENCY_URL
import com.jt17.currencycrypto.utils.Constants.CURR_TXT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @[Provides Singleton]
    fun provideJsonGsonConvertor(): GsonConverterFactory = GsonConverterFactory.create()

    @[Provides Singleton]
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().run {
        readTimeout(15, TimeUnit.SECONDS)
        connectTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
    }.build()

    //    provide currencies api
    @[Provides Singleton Named(CURR_TXT)]
    fun provideCurrencyRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(CURRENCY_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .client(okHttpClient)
        .build()

    //     provide for crypto api
    @[Provides Singleton Named(CRY_TXT)]
    fun provideCryptoRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(CRYPTO_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .client(okHttpClient)
        .build()

}