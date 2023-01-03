package com.jt17.currencycrypto.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.ActivityMainBinding
import com.jt17.currencycrypto.fragments.CryptoFragment
import com.jt17.currencycrypto.fragments.CurrencyFragment
import com.jt17.currencycrypto.fragments.HomeFragment
import com.jt17.currencycrypto.fragments.SettingsFragment

//      https://api.coinlore.net/api/tickers/
//      https://cbu.uz/uz/arkhiv-kursov-valyut/json/
//      https://flagcdn.com/w160/uz.png

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(HomeFragment())
        fragmentNavigation()

    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrame, fragment)
        fragmentTransaction.commit()

    }

    private fun fragmentNavigation() {
        binding.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                //home fragment
                R.id.home -> changeFragment(HomeFragment())
                //currency fragment
                R.id.currency -> changeFragment(CurrencyFragment())
                //crypto fragment
                R.id.crypto -> changeFragment(CryptoFragment())
                //home fragment
                R.id.settings -> changeFragment(SettingsFragment())
            }
            true
        }
    }

}