package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.util.Log
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
import com.jt17.currencycrypto.databinding.FragmentCurrencyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.CurrencyAdapter
import com.jt17.currencycrypto.utils.BaseUtils.showToast
import com.jt17.currencycrypto.utils.Constants.LOG_TXT
import com.jt17.currencycrypto.utils.helpers.BounceEdgeEffectFactory
import com.jt17.currencycrypto.utils.helpers.MarginItemDecoration
import com.jt17.currencycrypto.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CurrencyFragment : Fragment(R.layout.fragment_currency) {
    private var _binding: FragmentCurrencyBinding? = null
    private lateinit var binding: FragmentCurrencyBinding

    private val currentAdapter: CurrencyAdapter by lazy { CurrencyAdapter() }
    private val viewModel by viewModels<CurrencyViewModel>()
    private var list: List<CurrencyModel> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrencyBinding.bind(view)
        _binding = binding

        setupRecycler()/* init recyclerview, adapters */
        initLiveData()/* init LiveData from viewModel */
        initLoadData()/* load data from viewModel */
        searchViewCurr()/* init searchView search currencies by name, rate and others */
        initClicks()/* init all clicks, listeners */

    }

    private fun initLoadData() = viewModel.getCurrencies()

    private fun initLiveData() = viewModel.currencyList.onEach { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.let { data ->
                    list = data
                    currentAdapter.submitList(data)
                }
                swipeRef(false)
            }

            Resource.Status.LOADING -> swipeRef(true)

            Resource.Status.ERROR -> {
                swipeRef(false)
                resource.data?.let {
                    list = it
                    currentAdapter.submitList(it)
                }
                showToast(resource.message)
            }

            Resource.Status.EMPTY -> {}
        }
    }.launchIn(lifecycleScope)

    private fun searchViewCurr() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val text = newText.trim().lowercase()
                if (text.isNotEmpty()) {
                    val filterResult = list.filter {
                        it.CcyNm_EN.trim().lowercase(Locale.getDefault())
                            .contains(text) || it.Rate.trim().lowercase(Locale.getDefault())
                            .contains(text) || it.Ccy.trim().lowercase(Locale.getDefault())
                            .contains(text)
                    }
                    currentAdapter.submitList(filterResult)
                } else {
                    currentAdapter.submitList(list)
                }
                return false
            }
        }
        binding.currSearch.setOnQueryTextListener(queryTextListener)
    }

    private fun setupRecycler() {
        val marginDecoration =
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.last_item_margin))

        binding.currencyRecyc.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currentAdapter
            addItemDecoration(marginDecoration)
            edgeEffectFactory = BounceEdgeEffectFactory()
        }

    }

    private fun initClicks() {
        currentAdapter.setOnFavItemClickListener {
            var isCurrencyAlreadyInFavorites = false
            /** this code checking when the add to favorites button is clicked, it checks whether this
             *  currency is named and whether there is a currency with this name in the favorites entity. **/
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getFavCurrencies(it.Ccy).collect { fav ->
                        if (fav == null && !isCurrencyAlreadyInFavorites) {
                            viewModel.insertFavCurrency(
                                FavCurrencyModel(
                                    it.Ccy,
                                    it.CcyNm_EN,
                                    it.Rate,
                                    it.CcyNm_RU,
                                    it.CcyNm_UZ,
                                    it.CcyNm_UZC,
                                    it.Diff
                                )
                            )
                            showToast(resources.getString(R.string.succ_add_to_fav_txt))
                            isCurrencyAlreadyInFavorites = true
                        } else if (fav != null && !isCurrencyAlreadyInFavorites) {
                            showToast(resources.getString(R.string.avai_fav_curr_txt))
                            isCurrencyAlreadyInFavorites = true
                        }
                    }
                }
            }
        }

        if (currentAdapter.currentList.isNotEmpty()) {
            binding.swipeContainer.setOnRefreshListener {
                initLoadData()
            }
            swipeRef(false)
        }
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
