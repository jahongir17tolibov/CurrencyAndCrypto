package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navigate by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyc()
        initClicks()
        setupLottie()

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
    }

    private fun initRecyc() {
//        binding.favouriteCurrRecyc.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupLottie() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}