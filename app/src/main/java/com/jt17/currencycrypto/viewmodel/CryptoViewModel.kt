package com.jt17.currencycrypto.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.models.CryptoModel
//import com.jt17.currencycrypto.repository.AppRepository
import com.jt17.currencycrypto.repository.MainRepository
import kotlinx.coroutines.*

class CryptoViewModel(private val mainRepository: MainRepository) : ViewModel() {
//    private val repository = AppRepository()

    val cryptoList = MutableLiveData<List<CryptoModel>>()
    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        onError("Exception Handled: ${t.localizedMessage}")
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getCryptoApi() {
        job = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = mainRepository.getCryptoCurrencies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cryptoList.postValue(response.body()!!.data)
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