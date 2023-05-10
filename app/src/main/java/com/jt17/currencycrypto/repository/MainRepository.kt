package com.jt17.currencycrypto.repository

import android.util.Log
import com.jt17.currencycrypto.data.local.AppDao
import com.jt17.currencycrypto.data.remote.RemoteDataSource
import com.jt17.currencycrypto.data.resource.Resource
import com.jt17.currencycrypto.models.*
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appDao: AppDao
) {

    /* fetching currency data from data source and if has connection to network caching data to Room  */
    suspend fun fetchCurrencies(): Flow<Resource<List<CurrencyModel>>?> = flow {
        emit(Resource.Loading())
        val localData = appDao.getAllCurrencyData()
        localData?.let { data ->
            if (data.isNotEmpty()) emit(Resource.Success(data))
        }

        val remoteData = remoteDataSource.fetchCurrData()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                appDao.deleteAndInsertCurrencies(it)
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(IO)

    /* fetching crypto data from data source and if has connection to network caching data to Room  */
    suspend fun fetchCryptos(): Flow<Resource<List<CryptoModel>>?> = flow {
        emit(Resource.Loading())
        val localData = appDao.getAllCryptoData()
        localData?.let { data ->
            if (data.isNotEmpty()) emit(Resource.Success(data))
        }

        val remoteData = remoteDataSource.fetchCryptoData()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                appDao.deleteAndInsertCryptos(it)
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(IO)

    fun getCurrencyName(name: String?): Flow<CurrencyModel>? = appDao.getCurrName(name)

    fun getCryptosName(name: String?): Flow<CryptoModel>? = appDao.getCryName(name)

}

