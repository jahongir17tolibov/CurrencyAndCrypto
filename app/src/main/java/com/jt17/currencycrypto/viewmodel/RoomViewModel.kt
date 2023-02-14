package com.jt17.currencycrypto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.repository.RoomRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RoomRepository

    init {
        repository = RoomRepository(application)
    }

    val getAllCurrencies = repository.getAllCurrencies
    val getAllCrypto = repository.getAllCrypto


    fun getOneCurr(currencyName: String): LiveData<CurrencyModel> {
        return repository.getOneCurr(currencyName)
    }
    fun getOneCry(cryptoName: String): LiveData<CryptoModel> {
        return repository.getOneCry(cryptoName)
    }


    fun deleteOneCurr(currency: CurrencyModel) = viewModelScope.launch(IO) {
        repository.deleteOneCurr(currency)
    }
    fun deleteOneCry(crypto: CryptoModel) = viewModelScope.launch(IO) {
        repository.deleteOneCry(crypto)
    }


    fun deleteAll() = viewModelScope.launch(IO) {
        repository.deleteAll()
    }

}