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

    fun getFlagsApiService(): Api {
        if (api == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://flagcdn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit!!.create(Api::class.java)
        }
        return api!!
    }

}