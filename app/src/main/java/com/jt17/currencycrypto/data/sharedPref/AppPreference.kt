package com.jt17.currencycrypto.data.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class AppPreference {

    companion object {
        private lateinit var sharedPref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private var shp: AppPreference? = null

        fun init(context: Context) {
            shp = AppPreference()
            sharedPref = context.getSharedPreferences("auth", MODE_PRIVATE)
            editor = sharedPref.edit()
        }

        fun getInstance() = shp!!

    }

    fun setNightModeState(state: Boolean) {
        editor.putBoolean("isNight", state)
        editor.apply()
    }
    fun loadNightModeState(): Boolean = sharedPref.getBoolean("isNight", false)


}