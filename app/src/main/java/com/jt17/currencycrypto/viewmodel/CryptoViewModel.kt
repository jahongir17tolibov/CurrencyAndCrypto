package com.jt17.currencycrypto.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.networking.NetworkState
import com.jt17.currencycrypto.models.CryptoModel
//import com.jt17.currencycrypto.repository.AppRepository
import com.jt17.currencycrypto.repository.MainRepository
import com.jt17.currencycrypto.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
//    private val repository: RoomRepository = RoomRepository(Application())

    val cryptoList = MutableLiveData<List<CryptoModel>>()
    val progress = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = error

    private var job: Job? = null

    @SuppressLint("NullSafeMutableLiveData")
    fun getCryptoApi() {
        viewModelScope.launch {
            when (val response = mainRepository.getCryptoCurrencies()) {
                is NetworkState.Success -> {
                    cryptoList.postValue(response.data.data)
                    progress.value = false
                }
                is NetworkState.Error -> {
                    onError("Error ${response.response.message()}")
                    if (response.response.code() == 401) {
                    } else {
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        error.value = message
        progress.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}