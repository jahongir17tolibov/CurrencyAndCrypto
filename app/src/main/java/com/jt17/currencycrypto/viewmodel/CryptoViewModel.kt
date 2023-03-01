package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jt17.currencycrypto.models.Result

@HiltViewModel
class CryptoViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _cryptoList = MutableLiveData<Result<CryptoIncomingModel>>()
    val cryptoList = _cryptoList

    fun getCryptos() {
        viewModelScope.launch {
//            try {
                mainRepository.fetchCryptos().collect {
                    _cryptoList.value = it
                }
//            } catch (_: Exception) {
//            }
        }
    }


//    private val repository: RoomRepository = RoomRepository(Application())

//    val cryptoList = MutableLiveData<List<CryptoModel>?>()
//    val progress = MutableLiveData<Boolean>()
//    private val error = MutableLiveData<String>()
//    val errorMessage: LiveData<String>
//        get() = error
//
//    private var job: Job? = null
//
//    fun getCryptoApi() {
//        viewModelScope.launch {
//            try {
//                when (val response = mainRepository.getCryptoCurrencies()) {
//                    is NetworkState.Success -> {
//                        cryptoList.postValue(response.data.data)
//                        progress.value = false
//                    }
//                    is NetworkState.Error -> {
//                        onError("Error ${response.response.message()}")
//                    }
//                }
//            } catch (_: Exception) {
//            }
//
//        }
//    }
//
//    private fun onError(message: String) {
//        error.value = message
//        progress.value = false
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        job?.cancel()
//    }

//    if (response.response.code() == 401) {
//    } else {
//    }
}