package com.jt17.currencycrypto.repository

import com.jt17.currencycrypto.data.local.AppDao
import com.jt17.currencycrypto.data.remote.RemoteDataSource
import com.jt17.currencycrypto.models.*
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
    suspend fun fetchCurrencies(): Flow<Result<List<CurrencyModel>>?> =
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

    /***/

    suspend fun insertFavCurr(favCurrencyModel: FavCurrencyModel) =
        appDao.insertToFavCurrencies(favCurrencyModel)

    fun getAllDataFavCurr(): Flow<List<FavCurrencyModel>> = appDao.getAllFavCurrencies()

    fun getFavCurrName(name: String?): Flow<FavCurrencyModel> = appDao.getFavCurrName(name)

    fun getCurrName(name: String?): Flow<CurrencyModel> = appDao.getCurrName(name)

    suspend fun clearAllFavouriteCurrencies() = appDao.deleteAllFavCurrencies()

    suspend fun deleteOneFavCurrency(favCurrencyModel: FavCurrencyModel) =
        appDao.deleteOneFavCurrency(favCurrencyModel)

    /***/

    suspend fun insertFavCry(favCryptoModel: FavCryptoModel) =
        appDao.insertToFavCryptos(favCryptoModel)

    fun getAllDataFavCry(): Flow<List<FavCryptoModel>> = appDao.getAllFavCryptos()

    fun getFavCryName(name: String?): Flow<FavCryptoModel> = appDao.getCryptoName(name)

    suspend fun clearAllFavouriteCrypto() = appDao.deleteAllFavCryptos()

    suspend fun deleteOneFavCrypto(favCryptoModel: FavCryptoModel) =
        appDao.deleteOneFavCrypto(favCryptoModel)

}

