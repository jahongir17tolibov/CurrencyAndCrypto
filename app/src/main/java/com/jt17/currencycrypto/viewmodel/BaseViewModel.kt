package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.repository.AppRepository

class BaseViewModel : ViewModel() {
    private val repository = AppRepository()

    val currencyList = MutableLiveData<List<CurrencyModel>>()
    val cryptoList = MutableLiveData<List<CryptoModel>>()

    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun loadResponse() {
        repository.getData(progress, currencyList, cryptoList, error)
    }

}