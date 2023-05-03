package com.jt17.currencycrypto.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentConverterBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.utils.BaseUtils
import com.jt17.currencycrypto.utils.BaseUtils.copyToClipBoard
import com.jt17.currencycrypto.utils.BaseUtils.idealDoubleResult
import com.squareup.picasso.Picasso
import java.util.*

class ConverterFragment : Fragment() {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val args: ConverterFragmentArgs by navArgs()
    private val uzsPrice by lazy { args.currModelk?.Rate?.toDoubleOrNull() }
    private val navigation by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConverterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        safeCurrencyArgs()
        initClicks()
        convertingValues()

    }

    @SuppressLint("SetTextI18n")
    private fun safeCurrencyArgs() {
        binding.uzsTxt.text = UZS
        val resultToCurr = 1 / uzsPrice!!

        binding.convCurrName.text = args.currModelk?.CcyNm_EN
        binding.convPricer.text = args.currModelk?.Rate
        binding.getstrSymbol.text = args.currModelk?.Ccy
        binding.bottomCcy.text = " " + args.currModelk?.Ccy
        binding.convResultPricer.text = String.format("%.5f", resultToCurr)

        val flags: String? = args.currModelk?.Ccy?.take(2)?.lowercase()

        Picasso.get().load("${args.flagsArg + flags}.png")
            .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
            .into(binding.uzbOrCryptoIcon)

        Picasso.get().load("${args.flagsArg + "uz"}.png")
            .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
            .into(binding.resultIcon)
    }

    @SuppressLint("SetTextI18n")
    private fun swipeCurrencies() {
        binding.uzsTxt.text = args.currModelk?.Ccy
        binding.getstrSymbol.text = UZS
        val resultToCurr = 1 / uzsPrice!!

        binding.convCurrName.text = binding.convCurrResultName.text.toString()
        binding.convResultPricer.text = binding.convPricer.text.toString()
        binding.convPricer.text = String.format("%.5f", resultToCurr)
        binding.sumTxt.text = " " + args.currModelk?.Ccy

        val flags: String? = args.currModelk?.Ccy?.take(2)?.lowercase()

        Picasso.get().load("${args.flagsArg + "uz"}.png")
            .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
            .into(binding.uzbOrCryptoIcon)

        Picasso.get().load("${args.flagsArg + flags}.png")
            .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
            .into(binding.resultIcon)

    }

    private fun convertingValues() {
        binding.editTxtForUzCurr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {

                val text = binding.editTxtForUzCurr.text.toString()

                when {
                    text.isNotEmpty() -> {
                        when {
                            binding.uzsTxt.text.toString().contains(UZS) -> {
                                val result = convertCurrency(text)
                                binding.convResult.text = result
                            }

                            binding.getstrSymbol.text.toString().contains(UZS) -> {
                                val swipedText = binding.editTxtForUzCurr.text.toString().toDouble()
                                val result = convertToUzsCurrency(swipedText.toString())
                                binding.convResult.text = result
                            }
                        }
                    }

                    text.isEmpty() -> binding.convResult.text = ZERO
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun convertCurrency(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times(uzsPrice!!)
            idealDoubleResult(resultValue!!)
        }
    }

    private fun convertToUzsCurrency(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times((1 / uzsPrice!!))
            idealDoubleResult(resultValue!!)
        }
    }

    private fun initClicks() {
        binding.copyTxt.setOnClickListener {
            copyToClipBoard(binding.editTxtForUzCurr, null)
        }

        binding.copyResult.setOnClickListener {
            copyToClipBoard(null, binding.convResult)
        }

        binding.exchangeLayout.setOnClickListener {
            when {
                binding.uzsTxt.text.toString().contains(UZS) -> {
                    swipeCurrencies()
                    fastEmptyTexts()
                }

                binding.getstrSymbol.text.toString().contains(UZS) -> {
                    safeCurrencyArgs()
                    fastEmptyTexts()
                }
            }
        }

        val action = ConverterFragmentDirections
        binding.backBtn.setOnClickListener {
            if (args.directionState) {
                navigation.navigate(action.actionConverterFragmentToCurrencyFragment())
            } else {
                navigation.navigate(action.actionConverterFragmentToFavCurrenciesFragment())
            }

        }
    }

    private fun fastEmptyTexts() = binding.apply {
        editTxtForUzCurr.setText(EMPTY_STR)
        convResult.text = EMPTY_STR
    }

    companion object {
        private const val UZS = "UZS"
        private const val ZERO = "0.0"
        private const val EMPTY_STR = ""
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