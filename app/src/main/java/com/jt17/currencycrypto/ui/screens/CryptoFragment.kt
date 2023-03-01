package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.ui.adapters.CryptoAdapter
import com.jt17.currencycrypto.databinding.FragmentCryptoBinding
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CryptoFragment : Fragment() {
    private var _binding: FragmentCryptoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CryptoViewModel>()
    private val cryptoAdapter by lazy { CryptoAdapter() }
    private var list: List<CryptoModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCryptoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val retrofitService = NetManager.getCryptoApiServices()
//        val mainRepository = MainRepository(retrofitService)

        initRecyc()
        initLiveData()
        initLoadData()
        searchCryptoCurrencies()

        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }
        binding.swipeContainer.isRefreshing = true

    }

    private fun initLoadData() {
        viewModel.getCryptoApi()
    }

    private fun initLiveData() {

        viewModel.run {
            cryptoList.observe(requireActivity()) {
                list = it
                cryptoAdapter.submitList(it)
            }

            progress.observe(requireActivity()) { progressPos ->
                binding.swipeContainer.isRefreshing = progressPos
            }

            errorMessage.observe(requireActivity(), Observer { error ->
            })
        }
    }

    private fun initRecyc() {
        binding.cryptoRecyc.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
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