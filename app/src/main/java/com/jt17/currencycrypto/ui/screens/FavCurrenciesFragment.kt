package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentFavCurrenciesBinding
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.FavCurrenciesAdapter
import com.jt17.currencycrypto.utils.BaseUtils.showToast
import com.jt17.currencycrypto.utils.helpers.BounceEdgeEffectFactory
import com.jt17.currencycrypto.viewmodels.CurrencyViewModel
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
        setupRecycler()
        initClicks()

    }

    private fun initLiveData() = viewModel.getAllFavCurrencies.observe(viewLifecycleOwner) { favourites ->
            if (favourites.isEmpty()) binding.apply {
                favIsEmptyTxt.isVisible = true
                recycFavCurrency.isVisible = false
            } else binding.apply {
                recycFavCurrency.isVisible = true
                favIsEmptyTxt.isVisible = false
            }

            favourites.map { fav ->
                val favName = fav.CountryCode
                viewModel.getCurrName(favName)?.observe(viewLifecycleOwner) { curr ->
                    if (curr != null) {
                        if (fav.Rate != curr.Rate) {
                            val model = FavCurrencyModel(
                                curr.Ccy,
                                curr.CcyNm_EN,
                                curr.Rate,
                                curr.CcyNm_RU,
                                curr.CcyNm_UZ,
                                curr.CcyNm_UZC,
                                curr.Diff
                            )
                            model.id = fav.id
                            viewModel.updateFavouriteCurrency(model)
                        }
                    }
                }
            }

            favCurrAdapter.submitList(favourites)

        }


    private fun setupRecycler() = binding.recycFavCurrency.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = favCurrAdapter
        edgeEffectFactory = BounceEdgeEffectFactory()
    }

    private fun initClicks() {
        binding.clearAllBtn.setOnClickListener {
            initClearAllDialog()/* dialog for delete all favourite currencies */
        }

        favCurrAdapter.setOnDeleteItemClickListener {
            viewModel.deleteOneFavouriteCurrency(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = FavCurrenciesFragmentDirections.actionFavCurrenciesFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private fun initClearAllDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete all your favorite currencies?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Clear all") { dialog, _ ->
                showToast("Your all favorite currencies deleted")
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