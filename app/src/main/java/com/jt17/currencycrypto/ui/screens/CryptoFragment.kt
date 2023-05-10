package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.resource.Resource
import com.jt17.currencycrypto.databinding.FragmentCryptoBinding
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.CryptoAdapter
import com.jt17.currencycrypto.utils.BaseUtils.showToast
import com.jt17.currencycrypto.utils.helpers.BounceEdgeEffectFactory
import com.jt17.currencycrypto.utils.helpers.MarginItemDecoration
import com.jt17.currencycrypto.viewmodels.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class CryptoFragment : Fragment(R.layout.fragment_crypto) {
    private var _binding: FragmentCryptoBinding? = null
    private lateinit var binding: FragmentCryptoBinding

    private val viewModel by viewModels<CryptoViewModel>()
    private val cryptoAdapter by lazy { CryptoAdapter() }
    private var list: List<CryptoModel> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCryptoBinding.bind(view)
        _binding = binding

        setupRecycler()
        initLiveData()
        initLoadData()
        searchCryptoCurrencies()
        initClicks()

    }

    private fun initLoadData() = viewModel.getCryptos()

    private fun initLiveData() = viewModel.cryptoList.onEach { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.let { data ->
                    list = data
                    cryptoAdapter.submitList(data)
                }
                swipeRef(false)
            }

            Resource.Status.LOADING -> swipeRef(true)

            Resource.Status.ERROR -> {
                swipeRef(false)

                resource.data?.let {
                    cryptoAdapter.submitList(it)
                }
                showToast(resource.message)
            }

            Resource.Status.EMPTY -> {}

        }
    }.launchIn(lifecycleScope)

    private fun setupRecycler() {
        val marginDecoration =
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.last_item_margin))

        binding.cryptoRecyc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
            addItemDecoration(marginDecoration)
            edgeEffectFactory = BounceEdgeEffectFactory()
//            scrollToPosition(0)
        }
    }

    private fun searchCryptoCurrencies() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val text = newText.trim().lowercase()
                if (text.isNotEmpty()) {
                    val filterCryptoList = list.filter {
                        it.name.trim().lowercase(Locale.getDefault())
                            .contains(text) || it.symbol.trim().lowercase(Locale.getDefault())
                            .contains(text) || it.market_cap_usd.trim()
                            .lowercase(Locale.getDefault())
                            .contains(text)
                    }
                    cryptoAdapter.submitList(filterCryptoList)
                } else {
                    cryptoAdapter.submitList(list)
                }
                return false
            }
        }
        binding.crySearch.setOnQueryTextListener(queryTextListener)
    }

    private fun initClicks() {
        cryptoAdapter.setOnFavItemClickListener {
            /** this code checking when the add to favorites button is clicked, it checks whether this crypto
             *  currency is named and whether there is a crypto currency with this name in the favorites entity. **/
            var already = false
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getFavCryptos(it.name).collect { fav ->
                        if (fav == null && !already) {
                            viewModel.insertFavCryptos(
                                FavCryptoModel(
                                    it.name,
                                    it.symbol,
                                    it.rank,
                                    it.price_usd,
                                    it.price_btc
                                )
                            )
                            showToast("Crypto successfully added to favorites!")
                            already = true
                        } else if (fav != null && !already) {
                            showToast("This Crypto is available in favorites")
                            already = true
                        }
                    }
                }
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }
        swipeRef(false)

    }

    private fun swipeRef(state: Boolean) {
        binding.swipeContainer.isRefreshing = state
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNav.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}