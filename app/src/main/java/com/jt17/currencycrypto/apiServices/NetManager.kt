package com.jt17.currencycrypto.apiServices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetManager {
    var retrofit: Retrofit? = null
    var api: Api? = null

    fun getCurrencyApiService(): Api {
        if (api == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://cbu.uz/uz/arkhiv-kursov-valyut/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit!!.create(Api::class.java)
        }
        return api!!
    }

    fun getCryptoApiServices(): Api {
        if (api == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.coinlore.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit!!.create(Api::class.java)
        }
        return  api!!
    }

}