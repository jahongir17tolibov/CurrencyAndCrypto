package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.FragmentHomeBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.pow
import kotlin.math.sqrt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()
        setupAnimations()

    }

    private fun changeTheme() {
        val pos = when (!AppPreference.getInstance().loadNightModeState()) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }
        AppPreference.getInstance().setNightModeState(pos)

    }

    private fun initClicks() {
        binding.changeTheme.setOnClickListener {
            AppPreference.getInstance().loadNightModeState() != AppPreference.getInstance()
                .loadNightModeState()
            changeTheme()
            requireActivity().recreate()
        }

        binding.favCurrenciespath.setOnClickListener {
            navigation.navigate(R.id.favCurrenciesFragment)
        }

        binding.favCryptoPath.setOnClickListener {
            navigation.navigate(R.id.favCryptoFragment)
        }

    }

    private fun setupAnimations() {
        binding.lottie1.apply {
            setAnimation(R.raw.abstract1_night)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }

        binding.lottie2.apply {
            setAnimation(R.raw.fadding_cube_nighht)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }

        binding.vaweLottie.apply {
            setAnimation(R.raw.lines_waves)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }

        binding.favCurrenciesLottie.apply {
            setAnimation(R.raw.currency_lottie)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }

        binding.favCryptoLottie.apply {
            setAnimation(R.raw.crypro_lottie)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }

    override fun onResume() {
        super.onResume()

        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNav.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}