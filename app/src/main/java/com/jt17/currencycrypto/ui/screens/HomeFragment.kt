package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentHomeBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import com.jt17.currencycrypto.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var binding: FragmentHomeBinding

    private val navigation by lazy { findNavController() }
    private val viewModel by viewModels<ThemeViewModel>() {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        _binding = binding

        initClicks()
        setupAnimations()

    }

    private val changeThemeViewModel = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.themeState.collect { isNightModeEnabled ->
                changeTheme(isNightModeEnabled)
                Log.d(LOG_TXT, "in started $isNightModeEnabled")
            }
        }
    }

    private fun changeTheme(state: Boolean) = when (state) {
        true -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            true
        }

        false -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            false
        }
    }

    private fun initClicks() {
        binding.changeTheme.setOnClickListener {
            Log.d(LOG_TXT, "value in thread: ${!viewModel.themeState.value}")
            viewModel.setThemeState(!viewModel.themeState.value)
            changeThemeViewModel.runCatching {
                requireActivity().recreate()
                Log.d(LOG_TXT, "activity recreated!!!")
            }
        }

        binding.favCurrenciespath.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFavCurrenciesFragment()
            navigation.navigate(action)
        }

        binding.favCryptoPath.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFavCryptoFragment()
            navigation.navigate(action)
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