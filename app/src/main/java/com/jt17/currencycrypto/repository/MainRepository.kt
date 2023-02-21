package com.jt17.currencycrypto.repository

import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.networking.ApiService
import com.jt17.currencycrypto.networking.NetworkState
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    @Named("api1") private val apiService1: ApiService,
    @Named("api2") private val apiService2: ApiService
) {
    //get central banks currency
    suspend fun getUzCurrencies(): NetworkState<List<CurrencyModel>> {
        val response = apiService1.getCurrencyApi()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }


    //get crypto currencies
    suspend fun getCryptoCurrencies(): NetworkState<CryptoIncomingModel<List<CryptoModel>>> {
        val response = apiService2.getCryptoApi()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

}

