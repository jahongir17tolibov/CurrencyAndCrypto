package com.jt17.currencycrypto.Networking

import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object NetManager {

    private var currRetrofitService: ApiService? = null

    private val retrofitCur: Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://cbu.uz/uz/arkhiv-kursov-valyut/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCurrencyApiService(): ApiService {
        if (currRetrofitService == null) {
            val retrofit = retrofitCur
            currRetrofitService = retrofit.create(ApiService::class.java)
        }
        return currRetrofitService!!
    }

    private var cryRetrofitService: ApiService? = null

    private val retrofitCry: Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://api.coinlore.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCryptoApiServices(): ApiService {
        if (cryRetrofitService == null) {
            val retrofit = retrofitCry
            cryRetrofitService = retrofit.create(ApiService::class.java)
        }
        return cryRetrofitService!!
    }
}


