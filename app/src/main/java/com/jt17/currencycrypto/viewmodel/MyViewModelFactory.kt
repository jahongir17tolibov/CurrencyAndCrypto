package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jt17.currencycrypto.repository.MainRepository

class MyViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            BaseViewModel(this.mainRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel not found")
        }
    }
}