package com.jt17.currencycrypto.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.repository.MainRepository
import com.jt17.currencycrypto.room.AppDatabase
import com.jt17.currencycrypto.room.UserDao
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class BaseViewModel(private val mainRepository: MainRepository) : ViewModel() {

//    var db: UserDao = AppDatabase.getDatabaseClient(Application()).userDao()
    val currencyList = MutableLiveData<List<CurrencyModel>>()
    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        onError("Exception Handled: ${t.localizedMessage}")
    }

    fun getCurrencyApi() {
        job = viewModelScope.launch(IO + exceptionHandler) {
            val response = mainRepository.getUzCurrencies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    currencyList.postValue(response.body())
                    progress.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}