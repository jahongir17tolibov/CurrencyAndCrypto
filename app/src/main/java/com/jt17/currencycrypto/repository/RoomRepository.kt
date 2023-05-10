package com.jt17.currencycrypto.repository

import com.jt17.currencycrypto.data.local.AppDao
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

    suspend fun insertFavCurr(favCurrencyModel: FavCurrencyModel) {
        appDao.insertToFavCurrencies(favCurrencyModel)
    }

    fun getAllDataFavCurr(): Flow<List<FavCurrencyModel>> = appDao.getAllFavCurrencies()

    fun getFavCurrName(name: String?): Flow<FavCurrencyModel> = appDao.getFavCurrName(name)

    suspend fun clearAllFavouriteCurrencies() = appDao.deleteAllFavCurrencies()

    suspend fun deleteOneFavCurrency(favCurrencyModel: FavCurrencyModel) {
        appDao.deleteOneFavCurrency(favCurrencyModel)
    }

    suspend fun updateFavCurrency(favCurrencyModel: FavCurrencyModel) {
        appDao.upDateFavCurrency(favCurrencyModel)
    }

    /***/

    suspend fun insertFavCry(favCryptoModel: FavCryptoModel) {
        appDao.insertToFavCryptos(favCryptoModel)
    }

    fun getAllDataFavCry(): Flow<List<FavCryptoModel>> = appDao.getAllFavCryptos()

    fun getFavCryName(name: String?): Flow<FavCryptoModel> = appDao.getCryptoName(name)

    suspend fun clearAllFavouriteCrypto() = appDao.deleteAllFavCryptos()

    suspend fun deleteOneFavCrypto(favCryptoModel: FavCryptoModel) {
        appDao.deleteOneFavCrypto(favCryptoModel)
    }

    suspend fun updateFavCrypto(favCryptoModel: FavCryptoModel) {
        appDao.upDateFavCrypto(favCryptoModel)
    }

}