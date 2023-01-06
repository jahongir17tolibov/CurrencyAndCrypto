package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.repository.AppRepository

class CryptoViewModel: ViewModel() {
    private val repository = AppRepository()

    val cryptoList = MutableLiveData<List<CryptoModel>>()

    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun loadResponseBit() {
        repository.getCryptoData(progress, cryptoList, error)
    }

}