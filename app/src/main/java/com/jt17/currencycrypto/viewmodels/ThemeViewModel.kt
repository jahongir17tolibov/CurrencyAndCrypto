package com.jt17.currencycrypto.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _themeState = MutableStateFlow(AppPreference.getInstance().loadNightModeState())
    val themeState: StateFlow<Boolean> get() = _themeState

    fun setThemeState(state: Boolean) = viewModelScope.launch {
        AppPreference.getInstance().setNightModeState(state)
        _themeState.value = state
    }

}