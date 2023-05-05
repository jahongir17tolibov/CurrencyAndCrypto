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

        fun getInstance(): AppPreference = shp ?: throw IllegalStateException("Pref not found!!!")

    }

    fun setNightModeState(state: Boolean) = editor.run {
        putBoolean("isNight", state)
        apply()
    }


    fun loadNightModeState(): Boolean = sharedPref.getBoolean("isNight", false)

    fun setAppsLang(lang: String) = editor.apply {
        putString("appsLang", lang)
        apply()
    }


    fun getAppsLang(): String = sharedPref.getString("appsLang", "en") ?: "i don't know"

    fun setDate(str: String) {
        editor.putString("date", str)
        editor.apply()
    }

    fun getDate(): String = sharedPref.getString("date", "empty") ?: "no internet))"

}