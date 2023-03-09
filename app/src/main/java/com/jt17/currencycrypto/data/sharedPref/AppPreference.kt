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

    fun setAddFavCurrButton(state: Boolean) {
        editor.putBoolean("inFav2", state)
        editor.apply()
    }

    fun getAddFavCurrButton(): Boolean = sharedPref.getBoolean("inFav2", false)

    fun setDate(str: String) {
        editor.putString("date", str)
        editor.apply()
    }

    fun getDate(): String = sharedPref.getString("date", "empty") ?: "no internet))"

}