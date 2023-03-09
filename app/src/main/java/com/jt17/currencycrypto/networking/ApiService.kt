package com.jt17.currencycrypto.networking

import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    //Currencies api by central bank of Uzbekistan
    @GET("json/")
    suspend fun getCurrencyApi(): Response<List<CurrencyModel>>

    //Crypto api
    @GET("tickers/")
    suspend fun getCryptoApi(): Response<CryptoIncomingModel>

}