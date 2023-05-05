package com.jt17.currencycrypto.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.FragmentSettingsBinding
import com.jt17.currencycrypto.utils.BaseUtils.showToast
import com.jt17.currencycrypto.utils.Constants.APP_VERSION
import com.jt17.currencycrypto.utils.Constants.CRY_TXT
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import com.jt17.currencycrypto.utils.ContextUtils
import java.util.Locale

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

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("QueryPermissionsNeeded", "SuspiciousIndentation", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.text = AppPreference.getInstance().getDate()

        binding.appVersionTxt.text = "${resources.getString(R.string.app_ver_txt)} $APP_VERSION"

        binding.shareToFriend.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, resources.getString(R.string.share_txt)))
        }

        binding.contactToDev.setOnClickListener {
            try {
                val telegramIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=JT1771TJ"))
//                telegramIntent.setPackage("org.telegram.messenger")
                startActivity(telegramIntent)
            } catch (e: Exception) {
                showToast(resources.getString(R.string.telegram_error))
            }
        }

        binding.currenciesDialog.setOnClickListener {
            initCurrenciesApiInfoDialog()
        }

        binding.cryptosDialog.setOnClickListener {
            initCryptoApiInfoDialog()
        }

        binding.changeLanguageBtn.setOnClickListener {
            initChangeAppLangDialog()
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initChangeAppLangDialog() {
        val eng = resources.getString(R.string.english_txt)
        val rus = resources.getString(R.string.rus_txt)
        val uzb = resources.getString(R.string.uzbek_txt)
        val languageList = arrayOf(eng, rus, uzb)

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.select_dialog_item,
            languageList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                return view!!
            }
        }

        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle(resources.getString(R.string.select_lang_dialog_txt))
            .setItems(languageList) { _, which ->
                val locale = when (which) {
                    0 -> Locale("en")
                    1 -> Locale("ru")
                    2 -> Locale("uz")
                    else -> Locale.getDefault()
                }
                AppPreference.getInstance().setAppsLang(locale.language)
                Log.d(LOG_TXT, "initChangeAppLangDialog: ${locale.language}")
                context?.let { ContextUtils.updateLocale(it, locale) }.runCatching {
                    requireActivity().recreate()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}