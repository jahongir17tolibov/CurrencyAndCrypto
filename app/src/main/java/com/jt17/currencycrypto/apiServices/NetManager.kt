package com.jt17.currencycrypto.apiServices

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object NetManager {

    private val retrofitCur: Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://cbu.uz/uz/arkhiv-kursov-valyut/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val currenciesapi: Api by lazy {
        retrofitCur!!.create(Api::class.java)
    }


    fun getCurrencyApiService(): Api = currenciesapi

    //        if (currenciesapi == null) {
//            retrofit = Retrofit.Builder().baseUrl("https://cbu.uz/uz/arkhiv-kursov-valyut/")
//                .addConverterFactory(GsonConverterFactory.create()).build()
//            currenciesapi = retrofit!!.create(Api::class.java)
//        }

    private val retrofitCry: Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://api.coinlore.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val cryptoapi: Api by lazy {
        retrofitCry!!.create(Api::class.java)
    }

    fun getCryptoApiServices(): Api = cryptoapi
//        if (cryptoapi == null) {
//            retrofit = Retrofit.Builder().baseUrl("https://api.coinlore.net/api/")
//                .addConverterFactory(GsonConverterFactory.create()).build()
//            cryptoapi = retrofit!!.create(Api::class.java)
//        }
}


