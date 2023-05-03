package com.jt17.currencycrypto.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.FragmentSettingsBinding
import com.jt17.currencycrypto.utils.Constants.APP_VERSION

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: MaterialAlertDialogBuilder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded", "SuspiciousIndentation", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.text = AppPreference.getInstance().getDate()

        binding.appVersionTxt.text = "App version $APP_VERSION"

        binding.shareToFriend.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share the app with friends"))
        }

        binding.contactToDev.setOnClickListener {
            try {
                val telegramIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=JT1771TJ"))
//                telegramIntent.setPackage("org.telegram.messenger")
                startActivity(telegramIntent)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Telegram is not installed on your device",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.currenciesDialog.setOnClickListener {
            initCurrenciesApiInfoDialog()
        }

        binding.cryptosDialog.setOnClickListener {
            initCryptoApiInfoDialog()
        }


    }

    private fun initCurrenciesApiInfoDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setMessage(R.string.currencies_dialog_title)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun initCryptoApiInfoDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setMessage(R.string.crypto_dialog_title)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}