package com.jt17.currencycrypto.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.CurrencyLyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.ui.screens.FavCurrenciesFragmentDirections
import com.squareup.picasso.Picasso

class FavCurrenciesAdapter :
    ListAdapter<FavCurrencyModel, FavCurrenciesAdapter.ItemHolder>(FavCurrDiffUtil()) {

    companion object {
        //icon source link: https://flagpedia.net/download/api
        const val IMAGE_URL = "https://flagcdn.com/w160/"
    }

    inner class ItemHolder(val b: CurrencyLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: FavCurrencyModel) {
            b.pricer.text = result.Rate
            b.currName.text = result.CurrencyName_ENG
            b.country3str.text = result.CountryCode

            val flags: String = result.CountryCode.take(2).lowercase()

            Picasso.get().load("${CurrencyAdapter.IMAGE_URL + flags}.png")
                .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
                .into(b.flagAvatars)
        }


    }

    internal class FavCurrDiffUtil : DiffUtil.ItemCallback<FavCurrencyModel>() {

        override fun areItemsTheSame(
            oldItem: FavCurrencyModel,
            newItem: FavCurrencyModel
        ): Boolean {
            return oldItem.CurrencyName_ENG == newItem.CurrencyName_ENG
        }

        override fun areContentsTheSame(
            oldItem: FavCurrencyModel,
            newItem: FavCurrencyModel
        ): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CurrencyLyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = getItem(position)

        holder.bind(itemData)

        initClicks(holder, itemData)

        holder.b.diffCardView.isVisible = false
        holder.b.deleteFavCurrency.isVisible = true
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)

    }

    private var onItemClickListener: ((FavCurrencyModel) -> Unit)? = null

    fun setOnDeleteItemClickListener(listener: ((FavCurrencyModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    private fun initClicks(holder: ItemHolder, itemData: FavCurrencyModel) {
        holder.itemView.setOnClickListener {
            val navController = it.findNavController()
            val action =
                FavCurrenciesFragmentDirections.actionFavCurrenciesFragmentToConverterFragment(
                    CurrencyModel(
                        itemData.CountryCode,
                        itemData.CurrencyName_RU,
                        itemData.CurrencyName_UZ,
                        itemData.CurrencyName_UZK,
                        itemData.CurrencyName_ENG,
                        itemData.Rate,
                        itemData.Diff,
                        "",
                        itemData.id
                    ),
                    IMAGE_URL
                )
            navController.navigate(action)
        }

        holder.b.deleteFavCurrency.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

    }

}