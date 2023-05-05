package com.jt17.currencycrypto.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Locale

class ContextUtils(base: Context) : ContextWrapper(base) {

    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        fun updateLocale(context: Context, localeToSwitchTo: Locale): ContextUtils {
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(localeToSwitchTo)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return ContextUtils(context)
        }
    }

}