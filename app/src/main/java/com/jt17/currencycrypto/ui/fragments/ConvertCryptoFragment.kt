package com.jt17.currencycrypto.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentConvertCryptoBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.utils.BaseUtils
import com.squareup.picasso.Picasso

class ConvertCryptoFragment : Fragment() {
    private var _binding: FragmentConvertCryptoBinding? = null
    private val binding get() = _binding!!

    private val args: ConvertCryptoFragmentArgs by navArgs()
    private val btcPrice by lazy { args.cryptoValues?.price_btc?.toFloatOrNull() }
    private val usdPrice by lazy { args.cryptoValues?.price_usd?.toDoubleOrNull() }

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

        safeCryptoArgs()
        initClicks()
        convertResults()

    }

    private fun safeCryptoArgs() {
        binding.convCryptoName.text = args.cryptoValues?.symbol
        binding.convCryptoToUsdName.text = args.cryptoValues?.symbol
        binding.topCryptoName.text = args.cryptoValues?.symbol
        binding.topCryptoNameUsd.text = args.cryptoValues?.symbol
        binding.convCryptoBtcPricer.text = btcPrice.toString()
        binding.convUsdResultPricer.text = usdPrice.toString()

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

            override fun afterTextChanged(p0: Editable?) {
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
            val resultValue = input.toFloatOrNull()?.times(btcPrice!!)
            resultValue!!.toString()
        }
    }

    private fun convertToUSD(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times(usdPrice!!)
            BaseUtils.idealDoubleResult(resultValue!!).toString()
        }
    }

    private fun initClicks() {
        binding.copyTxt.setOnClickListener {
            BaseUtils.copyToClipBoard(
                binding.editTxtForCrypto,
                null,
                requireActivity(),
                requireContext()
            )
        }

        binding.copyBtcResult.setOnClickListener {
            BaseUtils.copyToClipBoard(
                null,
                binding.convBtcResult,
                requireActivity(),
                requireContext()
            )
        }

        binding.copyUsdResult.setOnClickListener {
            BaseUtils.copyToClipBoard(
                null,
                binding.convUsdResult,
                requireActivity(),
                requireContext()
            )
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