package com.jt17.currencycrypto.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.adapters.CurrencyAdapter
import com.jt17.currencycrypto.databinding.FragmentCurrencyBinding
import com.jt17.currencycrypto.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private val currentAdapter by lazy { CurrencyAdapter() }
    private val viewModel: BaseViewModel by lazy { ViewModelProvider(requireActivity())[BaseViewModel::class.java] }

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

        initRecyc()
        initLiveData()
        initLoadData()

        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }
        binding.swipeContainer.isRefreshing = true
    }

    private fun initLoadData() {
        viewModel.loadResponse()
    }

    private fun initLiveData() {

        viewModel.run {
            currencyList.observe(viewLifecycleOwner, Observer {
                currentAdapter.newList(it)
            })

            progress.observe(viewLifecycleOwner, Observer { progressPos ->
                binding.swipeContainer.isRefreshing = progressPos
            })

            error.observe(viewLifecycleOwner, Observer { error ->
            })
        }

    }

    private fun initRecyc() {
        binding.currencyRecyc.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currentAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}