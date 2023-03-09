package com.jt17.currencycrypto.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//      https://api.coinlore.net/api/tickers/
//      https://cbu.uz/uz/arkhiv-kursov-valyut/json/
//      https://flagcdn.com/w160/uz.png
//      https://coinicons-api.vercel.app/api/icon/btc
//      https://coinicons-api.vercel.app/api/icon/yfi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        darkLightTheme()/* to determine dark light theme */
//        starAddedVisibilityChecking()/* to determine starAdd and notAdd visibilities */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFrame) as NavHostFragment
        navController = navHostFragment.navController

        setupWithNavController(binding.navigationView, navController)

    }

    private fun darkLightTheme() {
        if (AppPreference.getInstance().loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun starAddedVisibilityChecking() {
        if (AppPreference.getInstance().getAddFavCurrButton()) {
            findViewById<ImageView>(R.id.star_not_add_curr)!!.isVisible = false
            findViewById<ImageView>(R.id.star_added_curr)!!.isVisible = true
        } else {
            findViewById<ImageView>(R.id.star_not_add_curr)!!.isVisible = true
            findViewById<ImageView>(R.id.star_added_curr)!!.isVisible = false
        }
    }

}
