package com.jt17.currencycrypto.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.jt17.currencycrypto.data.resource.Resource
import com.jt17.currencycrypto.models.*
import com.jt17.currencycrypto.repository.MainRepository
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class CryptoViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _cryptoList = MutableStateFlow<Resource<List<CryptoModel>>>(Resource.Empty)
    val cryptoList: StateFlow<Resource<List<CryptoModel>>> get() = _cryptoList

    fun getCryptos() = viewModelScope.launch {
        mainRepository.fetchCryptos().collect { data ->
            data?.let {
                _cryptoList.value = it
            } ?: "Data is nullable"
        }
    }

    val getAllFavCryptos: LiveData<List<FavCryptoModel>> =
        mainRepository.getAllDataFavCry().asLiveData()

    fun insertFavCryptos(favCryptoModel: FavCryptoModel) = viewModelScope.launch(IO) {
        mainRepository.insertFavCry(favCryptoModel)
    }

    fun getFavCryptos(name: String?): Flow<FavCryptoModel?> = mainRepository.getFavCryName(name)

    fun clearAllFavouriteCryptos() = viewModelScope.launch(IO) {
        mainRepository.clearAllFavouriteCrypto()
    }

    fun deleteOneFavouriteCryptos(favCryptoModel: FavCryptoModel) = viewModelScope.launch(IO) {
        mainRepository.deleteOneFavCrypto(favCryptoModel)
    }
}