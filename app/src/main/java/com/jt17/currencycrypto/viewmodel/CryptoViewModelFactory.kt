package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jt17.currencycrypto.repository.MainRepository

class CryptoViewModelFactory(private val mainRepository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CryptoViewModel::class.java)) {
            CryptoViewModel(this.mainRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel not found")
        }
    }

}