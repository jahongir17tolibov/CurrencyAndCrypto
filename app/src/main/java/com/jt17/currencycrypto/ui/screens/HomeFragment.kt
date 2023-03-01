package com.jt17.currencycrypto.ui.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentHomeBinding
import com.jt17.currencycrypto.utils.BaseUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navigate by lazy { findNavController() }
    private var isDarkTheme: Boolean = false
    private val prefs: SharedPreferences by lazy {
        requireContext().getSharedPreferences("theme", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (BaseUtils.themePosition) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
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
//        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
//        val themePreference = sharedPref.getString("theme", "light")
        if (BaseUtils.themePosition) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initClicks() {
        binding.changeTheme.setOnClickListener {
            BaseUtils.themePosition != BaseUtils.themePosition
            requireActivity().recreate()
//            parentFragmentManager.beginTransaction()
//                .detach(this)
//                .attach(this)
//                .commit()
        }
    }

    private fun initRecyc() {
        binding.favouriteCurrRecyc.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupLottie() {
        binding.lottie1.apply {
            setAnimation(R.raw.abstract1_light)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }

        binding.lottie2.apply {
            setAnimation(R.raw.fadding_cube_light)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}