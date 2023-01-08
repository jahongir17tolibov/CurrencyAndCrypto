package com.jt17.currencycrypto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jt17.currencycrypto.adapters.CryptoAdapter
import com.jt17.currencycrypto.databinding.FragmentCryptoBinding
import com.jt17.currencycrypto.viewmodel.BaseViewModel
import com.jt17.currencycrypto.viewmodel.CryptoViewModel
import kotlinx.coroutines.*

class CryptoFragment : Fragment() {
    private var _binding: FragmentCryptoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CryptoViewModel by lazy { ViewModelProvider(requireActivity())[CryptoViewModel::class.java] }

    private val cryptoAdapter by lazy { CryptoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCryptoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
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
        viewModel.loadResponseBit()
    }

    private fun initLiveData() {

        viewModel.run {
            cryptoList.observe(viewLifecycleOwner, Observer {
                cryptoAdapter.newList(it)
            })

            progress.observe(viewLifecycleOwner, Observer { progressPos ->
                binding.swipeContainer.isRefreshing = progressPos
            })

            error.observe(viewLifecycleOwner, Observer { error ->
            })
        }
    }


    private fun initRecyc() {
        binding.cryptoRecyc.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}