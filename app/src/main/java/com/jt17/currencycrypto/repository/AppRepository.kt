package com.jt17.currencycrypto.repository

import androidx.lifecycle.MutableLiveData
import com.jt17.currencycrypto.apiServices.NetManager
import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository {

    fun getData(
        progress: MutableLiveData<Boolean>,
        currencyList: MutableLiveData<List<CurrencyModel>>,
        cryptoList: MutableLiveData<List<CryptoModel>>,
        error: MutableLiveData<String>
    ) {

        /** Currencies api repository **/
        NetManager.getCurrencyApiService().getCurrencyApi()
            .enqueue(object : Callback<List<CurrencyModel>> {
                override fun onResponse(
                    call: Call<List<CurrencyModel>>,
                    response: Response<List<CurrencyModel>>
                ) {
                    if (response.isSuccessful) {
                        currencyList.value = response.body()
                        progress.value = false
                    }
                }

                override fun onFailure(call: Call<List<CurrencyModel>>, t: Throwable) {
                    error.value = t.localizedMessage?.toString()
                    progress.value = false
                }

            })

        /** Crypto api repository **/
        NetManager.getCryptoApiServices().getCryptoApi()
            .enqueue(object : Callback<CryptoIncomingModel<List<CryptoModel>>> {
                override fun onResponse(
                    call: Call<CryptoIncomingModel<List<CryptoModel>>>,
                    response: Response<CryptoIncomingModel<List<CryptoModel>>>
                ) {
                    if (response.isSuccessful) {
                        cryptoList.value = response.body()!!.data
                        progress.value = false
                    }
                }

                override fun onFailure(
                    call: Call<CryptoIncomingModel<List<CryptoModel>>>,
                    t: Throwable
                ) {
                    error.value = t.localizedMessage?.toString()
                    progress.value = false
                }

            })
    }

}