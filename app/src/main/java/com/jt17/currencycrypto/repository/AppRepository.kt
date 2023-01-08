package com.jt17.currencycrypto.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jt17.currencycrypto.apiServices.NetManager
import com.jt17.currencycrypto.models.CryptoIncomingModel
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableReplay.observeOn
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.reactivex.schedulers.Schedulers

class AppRepository {

    private val compositeDisposable = CompositeDisposable()

    fun getCurrencyData(
        progress: MutableLiveData<Boolean>,
        currencyList: MutableLiveData<List<CurrencyModel>>,
        error: MutableLiveData<String>
    ) {

        /** Currencies api repository **/
        val requestResult =
            NetManager.getCurrencyApiService().getCurrencyApi().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<CurrencyModel>>() {
                    override fun onNext(response: List<CurrencyModel>) {
                        try {
//                        Log.d("taggger", "${response.body()}")
                            if (response.isNotEmpty()) {
                                currencyList.value = response
                                progress.value = false
                            }
                        } catch (_: Exception) {
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage?.toString()
                        progress.value = false
                    }

                    override fun onComplete() {

                    }

                })

        compositeDisposable.add(requestResult)

    }

    fun getCryptoData(
        progress: MutableLiveData<Boolean>,
        cryptoList: MutableLiveData<List<CryptoModel>>,
        error: MutableLiveData<String>
    ) {

        /** Crypto api repository **/
        val requestResult =
            NetManager.getCryptoApiServices().getCryptoApi().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<CryptoIncomingModel<List<CryptoModel>>>() {
                    override fun onNext(response: CryptoIncomingModel<List<CryptoModel>>) {
                        if (response.data.isNotEmpty()) {
                            cryptoList.value = response.data
                            progress.value = false
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage?.toString()
                        progress.value = false
                    }

                    override fun onComplete() {
                    }

                })

        compositeDisposable.add(requestResult)

    }
}




















//.enqueue(object : Callback<List<CurrencyModel>> {
//                override fun onResponse(
//                    call: Call<List<CurrencyModel>>,
//                    response: Response<List<CurrencyModel>>
//                ) {
//                    try {
////                        Log.d("taggger", "${response.body()}")
//                        if (response.isSuccessful) {
//                            currencyList.value = response.body()
//                            progress.value = false
//                        }
//                    } catch (_: Exception) {
//                    }
//
//                }
//
//                override fun onFailure(call: Call<List<CurrencyModel>>, t: Throwable) {
//                    error.value = t.localizedMessage?.toString()
//                    progress.value = false
//                }
//
//            })


//    .enqueue(object : Callback<CryptoIncomingModel<List<CryptoModel>>> {
//                    override fun onResponse(
//                        call: Call<CryptoIncomingModel<List<CryptoModel>>>,
//                        response: Response<CryptoIncomingModel<List<CryptoModel>>>
//                    ) {
//                        try {
//                            if (response.isSuccessful) {
//                                Log.d("jhdsgfsdf", "${response.body()!!.data}")
//                                cryptoList.value = response.body()!!.data
//                                progress.value = false
//                            }
//                        } catch (_: Exception) {
//                        }
//                    }
//
//                    override fun onFailure(
//                        call: Call<CryptoIncomingModel<List<CryptoModel>>>,
//                        t: Throwable
//                    ) {
//                        error.value = t.localizedMessage?.toString()
//                        progress.value = false
//                    }
//
//                })
//
//    }