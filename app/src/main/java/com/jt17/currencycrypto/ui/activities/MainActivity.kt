package com.jt17.currencycrypto.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.ActivityMainBinding
import com.jt17.currencycrypto.utils.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

//      https://api.coinlore.net/api/tickers/
//      https://cbu.uz/uz/arkhiv-kursov-valyut/json/
//      https://flagcdn.com/w160/uz.png
//      https://coinicons-api.vercel.app/api/icon/btc
//      https://coinicons-api.vercel.app/api/icon/yfi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        appLanguage()
//        darkLightTheme/* to determine dark light theme */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFrame) as NavHostFragment
        navController = navHostFragment.navController

        setupWithNavController(binding.navigationView, navController)

    }

    private val darkLightTheme = if (AppPreference.getInstance().loadNightModeState()) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun appLanguage() {
        val appLang = AppPreference.getInstance().getAppsLang()
        val localeToSwitch = Locale(appLang)
        ContextUtils.updateLocale(this, localeToSwitch)
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun attachBaseContext(newBase: Context?) {
//        val appLang = AppPreference.getInstance().getAppsLang()
//        val localeToSwitch = Locale(appLang)
//        val localeUpdatedContext = newBase?.let { ContextUtils.updateLocale(it, localeToSwitch) }
//        super.attachBaseContext(localeUpdatedContext)
//    }

}
