package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.*
import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.models.FavCurrencyModel
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
            mainRepository.fetchCryptos().collect {
                _cryptoList.value = it
            }
        }
    }

    val getAllFavCryptos: LiveData<List<FavCryptoModel>> =
        mainRepository.getAllDataFavCry().asLiveData()

    fun insertFavCryptos(favCryptoModel: FavCryptoModel) = viewModelScope.launch {
        mainRepository.insertFavCry(favCryptoModel)
    }

    fun getFavCryptos(name: String?): LiveData<FavCryptoModel> =
        mainRepository.getFavCryName(name).asLiveData()

    fun clearAllFavouriteCryptos() = viewModelScope.launch {
        mainRepository.clearAllFavouriteCrypto()
    }

    fun deleteOneFavouriteCryptos(favCryptoModel: FavCryptoModel) = viewModelScope.launch {
        mainRepository.deleteOneFavCrypto(favCryptoModel)
    }
}