package com.jt17.currencycrypto.repository

import com.jt17.currencycrypto.data.local.AppDao
import com.jt17.currencycrypto.data.remote.RemoteDataSource
import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appDao: AppDao
) {

    /* fetching currency data from data source and if has connection to network caching data to Room  */
    suspend fun fetchCurrencies(): Flow<Result<List<CurrencyModel>>> =
        flow {
            emit(fetchCurrDataCached())
            emit(Result.loading())
            val response = remoteDataSource.fetchCurrData()

            if (response.status == Result.Status.SUCCESS) {
                response.data?.let {
                    appDao.run {
                        deleteCurrency(it)
                        insertCurrencies(it)
                    }
                }
            }
            emit(response)
        }.flowOn(IO)

    private fun fetchCurrDataCached(): Result<List<CurrencyModel>> =
        appDao.getAllCurrencyData().let {
            Result.success(it)
        }

    /* fetching crypto data from data source and if has connection to network caching data to Room  */
    suspend fun fetchCryptos(): Flow<Result<CryptoIncomingModel>?> =
        flow {
            emit(fetchCryDataCached())
            emit(Result.loading())
            val response = remoteDataSource.fetchCryptoData()

            if (response.status == Result.Status.SUCCESS) {
                response.data?.data?.let {
                    appDao.run {
                        deleteCrypto(it)
                        insertCrypto(it)
                    }
                }
            }
            emit(response)
        }.flowOn(IO)

    private fun fetchCryDataCached(): Result<CryptoIncomingModel> =
        appDao.getAllCryptoData().let {
            Result.success(CryptoIncomingModel(it))
        }


//    val getAllCurrencies: LiveData<List<CurrencyModel>> = appDao.getAllCurrencyData()

    //get central banks currency
//    suspend fun getUzCurrencies(): NetworkState<List<CurrencyModel>> {
//        val response = apiService1.getCurrencyApi()
//        return if (response.isSuccessful) {
//            val responseBody = response.body()
//            if (responseBody != null) {
//                NetworkState.Success(responseBody)
//            } else {
//                NetworkState.Error(response)
//            }
//        } else {
//            NetworkState.Error(response)
//        }
//    }
//
//
//    //get crypto currencies
//    suspend fun getCryptoCurrencies(): NetworkState<CryptoIncomingModel<List<CryptoModel>>> {
//        val response = apiService2.getCryptoApi()
//        return if (response.isSuccessful) {
//            val responseBody = response.body()
//            if (responseBody != null) {
//                NetworkState.Success(responseBody)
//            } else {
//                NetworkState.Error(response)
//            }
//        } else {
//            NetworkState.Error(response)
//        }
//    }

}

