package com.jt17.currencycrypto.repository

import com.jt17.currencycrypto.Networking.ApiService

class MainRepository(private val retrofitService: ApiService) {
    //get central banks currency
    suspend fun getUzCurrencies() = retrofitService.getCurrencyApi()

    //get crypto currencies
    suspend fun getCryptoCurrencies() = retrofitService.getCryptoApi()
}