package com.jt17.currencycrypto.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.db.AppDatabase
import com.jt17.currencycrypto.db.AppDao
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

//    private var db: AppDao = AppDatabase.getDatabaseClient(applicationContext).userDao()

    //get all currency data
    val getAllCurrencies: LiveData<List<CurrencyModel>> = appDao.getAllCurrencyData()

    //get all crypto data
    val getAllCrypto: LiveData<List<CryptoModel>> = appDao.getAllCryptoData()

    //get one currency
    fun getOneCurr(currencyName: String): LiveData<CurrencyModel> =
        appDao.getCurrencyName(currencyName)


    //get one crypto currency
    fun getOneCry(cryptoName: String): LiveData<CryptoModel> = appDao.getCryptoName(cryptoName)


    //delete one currency
    suspend fun deleteOneCurr(currency: CurrencyModel) = appDao.deleteCurrency(currency)


    //delete one crypto
    suspend fun deleteOneCry(crypto: CryptoModel) = appDao.deleteCrypto(crypto)


    //delete table
    suspend fun deleteAll() = appDao.deleteAll()

}