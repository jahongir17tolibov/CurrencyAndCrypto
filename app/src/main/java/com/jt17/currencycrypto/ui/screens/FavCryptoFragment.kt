package com.jt17.currencycrypto.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.FragmentFavCryptoBinding
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.ui.activities.MainActivity
import com.jt17.currencycrypto.ui.adapters.FavCryptoAdapter
import com.jt17.currencycrypto.viewmodel.CryptoViewModel
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
        initRecyc()
        initClicks()

    }

    private fun initLiveData() {
        viewModel.getAllFavCryptos.observe(viewLifecycleOwner) {
            favCryptoAdapter.submitList(it)
        }
    }

    private fun initRecyc() = binding.recycFavCrypto.run {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = favCryptoAdapter
    }

    private fun initClicks() {
        binding.clearAllBtn.setOnClickListener {
            initClearAllDialog()/* dialog for delete all favourite cryptos */
        }

        favCryptoAdapter.setOnDeleteClickListener {
            viewModel.deleteOneFavouriteCryptos(it)
        }

    }

    private fun initClearAllDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete all your favorite cryptos?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Clear all") { dialog, _ ->
                Toast.makeText(
                    requireContext(),
                    "Your all favorite cryptos deleted",
                    Toast.LENGTH_SHORT
                ).show()
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