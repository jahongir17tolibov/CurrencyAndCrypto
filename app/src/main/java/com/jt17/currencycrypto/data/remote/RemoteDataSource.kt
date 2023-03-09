package com.jt17.currencycrypto.data.remote

import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CurrencyModel
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject
import com.jt17.currencycrypto.models.Result
import com.jt17.currencycrypto.networking.ApiService
import javax.inject.Named


/**
 * fetches data from remote source
 */

class RemoteDataSource @Inject constructor(
    @Named("ret1") private val retrofit1: Retrofit,
    @Named("ret2") private val retrofit2: Retrofit
) {

    companion object {
        fun parseError(response: Response<*>, retrofit1: Retrofit, retrofit2: Retrofit): Error? {
            val converter1 =
                retrofit1.responseBodyConverter<Error>(Error::class.java, arrayOfNulls(0))
            val converter2 =
                retrofit2.responseBodyConverter<Error>(Error::class.java, arrayOfNulls(0))
            return try {
                converter1.convert(response.errorBody()!!)
                converter2.convert(response.errorBody()!!)
            } catch (e: IOException) {
                Error()
            }
        }
    }

    //fetching currencies api data
    suspend fun fetchCurrData(): Result<List<CurrencyModel>> {
        val currenciesService = retrofit1.create(ApiService::class.java)
        return getResponse(
            request = { currenciesService.getCurrencyApi() },
            defaultErrorMessage = "Error fetching Currencies list"
        )
    }

    //fetching crypto api data
    suspend fun fetchCryptoData(): Result<CryptoIncomingModel> {
        val cryptoService = retrofit2.create(ApiService::class.java)
        return getResponse(
            request = { cryptoService.getCryptoApi() },
            defaultErrorMessage = "Error fetching Crypto currency list"
        )
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            val response = request.invoke()
            if (response.isSuccessful) {
                return Result.success(response.body())
            } else {
                val errorResponse = parseError(response, retrofit1, retrofit2)
                Result.error(errorResponse?.localizedMessage ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown error", null)
        }
    }

}