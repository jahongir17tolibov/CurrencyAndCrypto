package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentFavCryptoBinding
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.FavCryptoAdapter
import com.jt17.currencycrypto.utils.BaseUtils.showToast
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import com.jt17.currencycrypto.utils.helpers.BounceEdgeEffectFactory
import com.jt17.currencycrypto.viewmodels.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavCryptoFragment : Fragment() {
    private var _binding: FragmentFavCryptoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CryptoViewModel>()
    private val navigation by lazy { findNavController() }
    private val favCryptoAdapter: FavCryptoAdapter by lazy { FavCryptoAdapter() }
    private lateinit var dialog: MaterialAlertDialogBuilder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavCryptoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()
        setupRecycler()
        initClicks()

    }

    private fun initLiveData() =
        viewModel.getAllFavCryptos.observe(viewLifecycleOwner) { favourite ->
            if (favourite.isEmpty()) binding.apply {
                favIsEmptyTxt.isVisible = true
                recycFavCrypto.isVisible = false
            } else binding.apply {
                favIsEmptyTxt.isVisible = false
                recycFavCrypto.isVisible = true
            }

            favourite.map { fav ->
                val favName = fav.name
                Log.d(LOG_TXT, "initLiveData: ${favName} ")
                viewModel.getCryPrice(favName)?.observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (fav.price_btc != it.price_btc || fav.price_usd != it.price_usd) {
                            val model = FavCryptoModel(
                                it.name,
                                it.symbol,
                                it.rank,
                                it.price_usd,
                                it.price_btc
                            )
                            model.id = fav.id
                            viewModel.updateFavouriteCryptos(model)
                        }
                    }
                }
            }

            favCryptoAdapter.submitList(favourite)

        }

    private fun setupRecycler() = binding.recycFavCrypto.run {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = favCryptoAdapter
        edgeEffectFactory = BounceEdgeEffectFactory()
    }

    private fun initClicks() {
        binding.clearAllBtn.setOnClickListener {
            initClearAllDialog()/* dialog for delete all favourite cryptos */
        }

        favCryptoAdapter.setOnDeleteClickListener {
            viewModel.deleteOneFavouriteCryptos(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = FavCryptoFragmentDirections.actionFavCryptoFragmentToHomeFragment()
            navigation.navigate(action)
        }

    }

    private fun initClearAllDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete all your favorite cryptos?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Clear all") { dialog, _ ->
                showToast("Your all favorite cryptos deleted")
                dialog.dismiss()
                viewModel.clearAllFavouriteCryptos()
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