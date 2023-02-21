package com.jt17.currencycrypto.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.networking.NetworkState
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    //    var db: UserDao = AppDatabase.getDatabaseClient(Application()).userDao()
    val currencyList = MutableLiveData<List<CurrencyModel>?>()
    val progress = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = error

    private var job: Job? = null

    fun getCurrencyApi() {
        viewModelScope.launch {
            when (val response = mainRepository.getUzCurrencies()) {
                is NetworkState.Success -> {
                    currencyList.postValue(response.data)
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