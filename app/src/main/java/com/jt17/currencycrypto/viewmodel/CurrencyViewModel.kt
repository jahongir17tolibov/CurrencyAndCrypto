package com.jt17.currencycrypto.viewmodel

import androidx.lifecycle.*
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jt17.currencycrypto.models.Result

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _currencyList = MutableLiveData<Result<List<CurrencyModel>>>()
    val currencyList = _currencyList

    fun getCurrencies() {
        viewModelScope.launch {
            mainRepository.fetchCurrencies().collect {
                _currencyList.value = it
            }
        }
    }

    val getAllFavCurrencies: LiveData<List<FavCurrencyModel>> =
        mainRepository.getAllDataFavCurr().asLiveData()

    fun insertFavCurrency(favCurrencyModel: FavCurrencyModel) = viewModelScope.launch {
        mainRepository.insertFavCurr(favCurrencyModel)
    }

    fun getFavCurrencies(ccy: String?): LiveData<FavCurrencyModel> =
        mainRepository.getFavCurrName(ccy).asLiveData()

    fun getCurrName(name: String?): LiveData<CurrencyModel> =
        mainRepository.getCurrName(name).asLiveData()

    fun clearAllFavouriteCurrencies() = viewModelScope.launch {
        mainRepository.clearAllFavouriteCurrencies()
    }

    fun deleteOneFavouriteCurrency(favCurrencyModel: FavCurrencyModel) = viewModelScope.launch {
        mainRepository.deleteOneFavCurrency(favCurrencyModel)
    }

}