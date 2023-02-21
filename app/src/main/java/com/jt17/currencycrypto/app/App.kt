package com.jt17.currencycrypto.app

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

//    companion object {
//        lateinit var app: App
//    }

//    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()

//        app = this

//        appComponent = DaggerA
//        appComponent.myInject(this)

    }
}