package com.jt17.currencycrypto.apiServices

import io.reactivex.Observable
import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    //Currencies api by central bank of Uzbekistan
    @GET("json/")
    fun getCurrencyApi(): Observable<List<CurrencyModel>>

    //Crypto api
    @GET("tickers/")
    fun getCryptoApi(): Observable<CryptoIncomingModel<List<CryptoModel>>>

}