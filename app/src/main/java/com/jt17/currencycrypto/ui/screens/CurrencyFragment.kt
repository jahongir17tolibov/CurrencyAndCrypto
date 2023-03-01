package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private val currentAdapter: CurrencyAdapter by lazy { CurrencyAdapter() }
    private val favCurrAdapter: FavCurrenciesAdapter by lazy { FavCurrenciesAdapter() }

    private val viewModel by viewModels<CurrencyViewModel>()

    //    private lateinit var roomViewModel: RoomViewModel
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

//        val retrofitService = NetManager.getCurrencyApiService()
//        val mainRepository = MainRepository(retrofitService)

//        roomViewModel = ViewModelProvider(requireActivity())[RoomViewModel::class.java]

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
            currencyList.observe(viewLifecycleOwner) {
                if (it != null) {
                    list = it
                }
                currentAdapter.submitList(it)
            }

            progress.observe(viewLifecycleOwner) { progressPos ->
                binding.swipeContainer.isRefreshing = progressPos
            }

            errorMessage.observe(viewLifecycleOwner) {
            }
        }

//        roomViewModel.run {
//            getAllCurrencies.observe(requireActivity()) {
//                currentAdapter.submitList(it)
//            }
//        }

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