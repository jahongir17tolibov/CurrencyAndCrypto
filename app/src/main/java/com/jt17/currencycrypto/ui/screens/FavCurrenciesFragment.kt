package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentFavCurrenciesBinding
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.FavCurrenciesAdapter
import com.jt17.currencycrypto.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavCurrenciesFragment : Fragment() {
    private var _binding: FragmentFavCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CurrencyViewModel>()
    private val favCurrAdapter: FavCurrenciesAdapter by lazy { FavCurrenciesAdapter() }
    private lateinit var dialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavCurrenciesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()
        initRecyc()
        initClicks()

    }

    private fun initLiveData() {
        viewModel.getAllFavCurrencies.observe(viewLifecycleOwner) {
            favCurrAdapter.submitList(it)
        }
    }

    private fun initRecyc() = binding.recycFavCurrency.run {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = favCurrAdapter
    }

    private fun initClicks() {
        binding.clearAllBtn.setOnClickListener {
            initClearAllDialog()/* dialog for delete all favourite currencies */
        }

        favCurrAdapter.setOnDeleteItemClickListener {
            viewModel.deleteOneFavouriteCurrency(it)
        }
    }

    private fun initClearAllDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete all your favorite currencies?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Clear all") { dialog, _ ->
                Toast.makeText(
                    requireContext(),
                    "Your all favorite currencies deleted",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
                viewModel.clearAllFavouriteCurrencies()
            }
        dialog.show()
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