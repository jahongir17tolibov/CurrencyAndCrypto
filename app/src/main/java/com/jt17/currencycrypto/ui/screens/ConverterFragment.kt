package com.jt17.currencycrypto.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentConverterBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.utils.BaseUtils
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class ConverterFragment : Fragment() {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val args: ConverterFragmentArgs by navArgs()
    private val uzsPrice by lazy { args.currModelk?.Rate?.toDoubleOrNull() }

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
        binding.uzsTxt.text = "UZS"
        val resultToCurr = 1 / uzsPrice!!

        binding.convCurrName.text = args.currModelk?.CcyNm_EN
        binding.convPricer.text = args.currModelk?.Rate
        binding.getstrSymbol.text = args.currModelk?.Ccy
        binding.bottomCcy.text = " " + args.currModelk?.Ccy
        binding.convResultPricer.text = String.format("%.4f", resultToCurr)

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
        binding.getstrSymbol.text = "UZS"
        val resultToCurr = 1 / uzsPrice!!

        binding.convCurrName.text = binding.convCurrResultName.text.toString()
        binding.convResultPricer.text = binding.convPricer.text.toString()
        binding.convPricer.text = BaseUtils.idealDoubleResult(resultToCurr)
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
                            binding.uzsTxt.text.toString().contains("UZS") -> {
                                val result = convertCurrency(text)
                                binding.convResult.text = result
                            }
                            binding.getstrSymbol.text.toString().contains("UZS") -> {
                                val swipedText = binding.editTxtForUzCurr.text.toString().toDouble()
                                val result = convertToUzsCurrency(swipedText.toString())
                                binding.convResult.text = result
                            }
                        }


                    }
                    text.isEmpty() -> binding.convResult.text = "0.0"
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun convertCurrency(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times(uzsPrice!!)
            BaseUtils.idealDoubleResult(resultValue!!)
        }
    }

    private fun convertToUzsCurrency(input: String): String {
        return run {
            val resultValue = input.toDoubleOrNull()?.times((1 / uzsPrice!!))
            BaseUtils.idealDoubleResult(resultValue!!)
        }
    }

    private fun initClicks() {
        binding.copyTxt.setOnClickListener {
            BaseUtils.copyToClipBoard(
                binding.editTxtForUzCurr,
                null,
                requireActivity(),
                requireContext()
            )
        }

        binding.copyResult.setOnClickListener {
            BaseUtils.copyToClipBoard(
                null, binding.convResult,
                requireActivity(),
                requireContext()
            )
        }

        binding.exchangeLayout.setOnClickListener {
            when {
                binding.uzsTxt.text.toString().contains("UZS") -> swipeCurrencies()
                binding.getstrSymbol.text.toString().contains("UZS") -> safeCurrencyArgs()
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