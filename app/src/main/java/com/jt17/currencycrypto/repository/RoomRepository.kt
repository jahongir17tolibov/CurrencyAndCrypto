package com.jt17.currencycrypto.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.room.AppDatabase
import com.jt17.currencycrypto.room.UserDao

class RoomRepository(applicationContext: Application) {

    var db: UserDao = AppDatabase.getDatabaseClient(applicationContext).userDao()

    //get all currency data
    val getAllCurrencies: LiveData<List<CurrencyModel>> = db.getAllCurrencyData()

    //get all crypto data
    val getAllCrypto: LiveData<List<CryptoModel>> = db.getAllCryptoData()

    //get one currency
    fun getOneCurr(currencyName: String): LiveData<CurrencyModel> {
        return db.getCurrencyName(currencyName)
    }

    //get one crypto currency
    fun getOneCry(cryptoName: String): LiveData<CryptoModel> {
        return db.getCryptoName(cryptoName)
    }

    //delete one currency
    suspend fun deleteOneCurr(currency: CurrencyModel) {
        db.deleteCurrency(currency)
    }

    //delete one crypto
    suspend fun deleteOneCry(crypto: CryptoModel) {
        db.deleteCrypto(crypto)
    }

    //delete table
    suspend fun deleteAll() {
        db.deleteAll()
    }

}