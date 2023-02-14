package com.jt17.currencycrypto.repository

//class AppRepository {
//
//    private val compositeDisposable = CompositeDisposable()
//
//    fun getCurrencyData(
//        progress: MutableLiveData<Boolean>,
//        currencyList: MutableLiveData<List<CurrencyModel>>,
//        error: MutableLiveData<String>
//    ) {
//
//        /** Currencies api repository **/
//        val requestResult =
//            NetManager.getCurrencyApiService().getCurrencyApi().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableObserver<List<CurrencyModel>>() {
//                    override fun onNext(response: List<CurrencyModel>) {
//                        try {
////                        Log.d("taggger", "${response.body()}")
//                            if (response.isNotEmpty()) {
//                                currencyList.value = response
//                                progress.value = false
//                            }
//                        } catch (_: Exception) {
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        error.value = e.localizedMessage?.toString()
//                        progress.value = false
//                    }
//
//                    override fun onComplete() {
//
//                    }
//
//                })
//
//        compositeDisposable.add(requestResult)
//
//    }
//
//    fun getCryptoData(
//        progress: MutableLiveData<Boolean>,
//        cryptoList: MutableLiveData<List<CryptoModel>>,
//        error: MutableLiveData<String>
//    ) {
//
//        /** Crypto api repository **/
//        val requestResult =
//            NetManager.getCryptoApiServices().getCryptoApi().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object :
//                    DisposableObserver<CryptoIncomingModel<List<CryptoModel>>>() {
//                    override fun onNext(response: CryptoIncomingModel<List<CryptoModel>>) {
//                        if (response.data.isNotEmpty()) {
//                            cryptoList.value = response.data
//                            progress.value = false
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        error.value = e.localizedMessage?.toString()
//                        progress.value = false
//                    }
//
//                    override fun onComplete() {
//                    }
//
//                })
//
//        compositeDisposable.add(requestResult)
//
//    }
//}
