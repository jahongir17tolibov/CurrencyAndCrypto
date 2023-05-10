package com.jt17.currencycrypto.app

import android.app.Application
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPreference.init(this)
    }
}