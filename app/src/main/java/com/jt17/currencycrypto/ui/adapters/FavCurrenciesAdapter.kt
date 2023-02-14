package com.jt17.currencycrypto.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.databinding.FavCurrencyItemLyBinding
import com.jt17.currencycrypto.models.CurrencyModel

class FavCurrenciesAdapter :
    ListAdapter<CurrencyModel, FavCurrenciesAdapter.ItemHolder>(FavCurrDiffUtil()) {

    inner class ItemHolder(val b: FavCurrencyItemLyBinding) : RecyclerView.ViewHolder(b.root) {

    }

    internal class FavCurrDiffUtil : DiffUtil.ItemCallback<CurrencyModel>() {

        override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
            return newItem.CcyNm_EN == oldItem.CcyNm_EN
        }

        override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            FavCurrencyItemLyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

    }

}