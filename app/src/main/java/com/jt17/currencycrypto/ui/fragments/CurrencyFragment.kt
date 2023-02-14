package com.jt17.currencycrypto.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.currencycrypto.Networking.NetManager
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.ui.adapters.CurrencyAdapter
import com.jt17.currencycrypto.databinding.FragmentCurrencyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.repository.MainRepository
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.FavCurrenciesAdapter
import com.jt17.currencycrypto.viewmodel.BaseViewModel
import com.jt17.currencycrypto.viewmodel.MyViewModelFactory
import com.jt17.currencycrypto.viewmodel.RoomViewModel
import java.util.*

class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private val currentAdapter: CurrencyAdapter by lazy { CurrencyAdapter() }
    private val favCurrAdapter: FavCurrenciesAdapter by lazy { FavCurrenciesAdapter() }
    private lateinit var viewModel: BaseViewModel
    private lateinit var roomViewModel: RoomViewModel
    private var list: List<CurrencyModel> = emptyList()
    private var favList: List<CurrencyModel> = emptyList()

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

        val retrofitService = NetManager.getCurrencyApiService()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(mainRepository)
        )[BaseViewModel::class.java]

        roomViewModel = ViewModelProvider(requireActivity())[RoomViewModel::class.java]

        initRecyc()
        initLiveData()
        initLoadData()
        searchViewCurr()
        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }
        binding.swipeContainer.isRefreshing = true

    }

    private fun initLoadData() {
        viewModel.getCurrencyApi()
    }

    private fun initLiveData() {

        viewModel.run {
            currencyList.observe(requireActivity()) {
                list = it
                currentAdapter.submitList(it)
            }

            progress.observe(requireActivity()) { progressPos ->
                binding.swipeContainer.isRefreshing = progressPos
            }

            error.observe(requireActivity()) {
            }
        }

        roomViewModel.run {
            getAllCurrencies.observe(requireActivity()) {
                currentAdapter.submitList(it)
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

        currentAdapter.setOnFavItemClickListener {
            favCurrAdapter.submitList(list)
        }
    }

    private fun initClicks() {

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
