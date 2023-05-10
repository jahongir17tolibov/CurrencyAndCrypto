package com.jt17.currencycrypto.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentConvertCryptoBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.utils.BaseUtils.copyToClipBoard
import com.jt17.currencycrypto.utils.BaseUtils.idealDoubleResult
import com.jt17.currencycrypto.utils.BaseUtils.showAutomaticallyKeyboard
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class ConvertCryptoFragment : Fragment() {
    private var _binding: FragmentConvertCryptoBinding? = null
    private val binding get() = _binding!!

    private val args: ConvertCryptoFragmentArgs by navArgs()
    private val btcPrice by lazy { args.cryptoValues?.price_btc?.toDoubleOrNull() }
    private val usdPrice by lazy { args.cryptoValues?.price_usd?.toDoubleOrNull() }
    private val navigation by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConvertCryptoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAutomaticallyKeyboard(binding.editTxtForCrypto)
        safeCryptoArgs()
        initClicks()
        convertResults()

    }

    private fun safeCryptoArgs() {
        binding.convCryptoName.text = args.cryptoValues?.symbol
        binding.convCryptoToUsdName.text = args.cryptoValues?.symbol
        binding.topCryptoName.text = args.cryptoValues?.symbol
        binding.topCryptoNameUsd.text = args.cryptoValues?.symbol
        binding.convCryptoBtcPricer.text = idealDoubleResult(btcPrice!!)
        binding.convUsdResultPricer.text = idealDoubleResult(usdPrice!!)

        val cryIcons: String = args.cryptoValues?.symbol!!.lowercase()
        Picasso.get().load(args.cryptoStr + cryIcons).error(R.color.black)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.cryptoIconConv)
    }

    private fun convertResults() {
        binding.editTxtForCrypto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                val text = binding.editTxtForCrypto.text.toString()

                when {
                    text.isNotEmpty() -> {
                        val resultBtc = convertCryptos(text)
                        binding.convBtcResult.text = resultBtc

                        val resultUsd = convertToUSD(text)
                        binding.convUsdResult.text = resultUsd
                    }

                    text.isEmpty() -> {
                        binding.convBtcResult.text = "0.0 BTC"
                        binding.convUsdResult.text = "$ 0.0"
                    }
                }
            }
        })
    }

    private fun convertCryptos(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times(btcPrice!!)
            idealDoubleResult(resultValue!!)
        }
    }

    private fun convertToUSD(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times(usdPrice!!)
            idealDoubleResult(resultValue!!)
        }
    }

    private fun initClicks() {
        binding.copyTxt.setOnClickListener {
            copyToClipBoard(binding.editTxtForCrypto, null)
        }

        binding.copyBtcResult.setOnClickListener {
            copyToClipBoard(null, binding.convBtcResult)
        }

        binding.copyUsdResult.setOnClickListener {
            copyToClipBoard(null, binding.convUsdResult)
        }

        val action = ConvertCryptoFragmentDirections
        binding.backBtn.setOnClickListener {
            if (args.directionState) {
                navigation.navigate(action.actionConvertCryptoFragment2ToCryptoFragment())
            } else {
                navigation.navigate(action.actionConvertCryptoFragment2ToFavCryptoFragment())
            }
        }

    }

    override fun onResume() {
        super.onResume()

        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNav.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}