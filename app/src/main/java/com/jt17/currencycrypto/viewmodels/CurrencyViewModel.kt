package com.jt17.currencycrypto.viewmodels

import androidx.lifecycle.*
import com.jt17.currencycrypto.data.resource.Resource
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _currencyList = MutableStateFlow<Resource<List<CurrencyModel>>>(Resource.Empty)
    val currencyList: StateFlow<Resource<List<CurrencyModel>>> = _currencyList

    fun getCurrencies() = viewModelScope.launch {
        mainRepository.fetchCurrencies().collect { data ->
            data?.let {
                _currencyList.value = it
            } ?: "Data is nullable"
        }
    }

    val getAllFavCurrencies: LiveData<List<FavCurrencyModel>> =
        mainRepository.getAllDataFavCurr().asLiveData()

    fun insertFavCurrency(favCurrencyModel: FavCurrencyModel) = viewModelScope.launch(IO) {
        mainRepository.insertFavCurr(favCurrencyModel)
    }

    fun getFavCurrencies(ccy: String?): Flow<FavCurrencyModel?> = mainRepository.getFavCurrName(ccy)

    fun clearAllFavouriteCurrencies() = viewModelScope.launch(IO) {
        mainRepository.clearAllFavouriteCurrencies()
    }

    fun deleteOneFavouriteCurrency(favCurrencyModel: FavCurrencyModel) = viewModelScope.launch(IO) {
        mainRepository.deleteOneFavCurrency(favCurrencyModel)
    }

}