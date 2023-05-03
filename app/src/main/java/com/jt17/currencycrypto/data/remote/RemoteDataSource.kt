package com.jt17.currencycrypto.data.remote

import com.jt17.currencycrypto.data.resource.Resource
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.networking.ApiService
import com.jt17.currencycrypto.utils.Constants.CRY_TXT
import com.jt17.currencycrypto.utils.Constants.CURR_TXT
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * fetches data from remote source
 */

class RemoteDataSource @Inject constructor(
    @Named(CURR_TXT) private val retrofit1: Retrofit,
    @Named(CRY_TXT) private val retrofit2: Retrofit
) {

    //fetching currencies api data
    suspend fun fetchCurrData(): Resource<List<CurrencyModel>> = getResponse {
        val currenciesApiService = retrofit1.create(ApiService::class.java)
        currenciesApiService.getCurrencyApi()
    }

    //fetching crypto api data
    suspend fun fetchCryptoData(): Resource<List<CryptoModel>> = try {
        val cryptoApiService = retrofit2.create(ApiService::class.java)
        val response = cryptoApiService.getCryptoApi()
        if (response.isSuccessful) {
            response.body()?.data?.let {
                Resource.Success(it)
            } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error("Response code: ${response.code()}")
        }
    } catch (e: Throwable) {
        Resource.Error(e.message ?: "An error occurred")
    }


    private suspend fun <T : Any> getResponse(
        request: suspend () -> Response<T>
    ): Resource<T> = try {
        val response = request.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error("Response code: ${response.code()}")
        }
    } catch (e: Throwable) {
        Resource.Error(e.message ?: "An error occurred")
    }

}