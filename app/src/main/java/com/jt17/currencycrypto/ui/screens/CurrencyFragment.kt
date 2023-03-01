package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentCurrencyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.CurrencyAdapter
import com.jt17.currencycrypto.ui.adapters.FavCurrenciesAdapter
import com.jt17.currencycrypto.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.jt17.currencycrypto.models.Result

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private val currentAdapter: CurrencyAdapter by lazy { CurrencyAdapter() }
    private val favCurrAdapter: FavCurrenciesAdapter by lazy { FavCurrenciesAdapter() }

    private val viewModel by viewModels<CurrencyViewModel>()

    private var list: List<CurrencyModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCurrencyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyc()/* init recyclerview, adapters */
        initLiveData()/* init LiveData from viewModel */
        initLoadData()/* load data from viewModel */
        searchViewCurr()/* init searchView search currencies by name, rate and others */
        initClicks()/* init all clicks, listeners */

    }

    private fun initLoadData() {
        viewModel.getCurrencies()
    }

    private fun initLiveData() {
        viewModel.currencyList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { data ->
                        list = data
                        val mList = data.toMutableList()
                        mList.clear()
                        val sortedList = data.sortedBy { it.Ccy }
                        mList.addAll(sortedList)
                        currentAdapter.submitList(mList)
                    }
                    binding.swipeContainer.isRefreshing = false
                }

                Result.Status.LOADING -> binding.swipeContainer.isRefreshing = true

                Result.Status.ERROR -> {
                    result?.message?.let {
                        Toast.makeText(
                            requireContext(),
                            "Check your internet connection!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    binding.swipeContainer.isRefreshing = false
                }
            }

        }


    }

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

    private fun initRecyc() {
        binding.currencyRecyc.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currentAdapter
        }
    }

    private fun initClicks() {
        currentAdapter.setOnFavItemClickListener {
            favCurrAdapter.submitList(list)
        }
        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }
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
