package com.jt17.currencycrypto.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.CryptoLyBinding
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.ui.screens.FavCryptoFragmentDirections
import com.jt17.currencycrypto.utils.Constants.IMAGE_CRYPTO_URL
import com.squareup.picasso.Picasso

class FavCryptoAdapter :
    ListAdapter<FavCryptoModel, FavCryptoAdapter.ItemHolder>(CryptoDiffUtil()) {

    inner class ItemHolder(val b: CryptoLyBinding) : RecyclerView.ViewHolder(b.root) {

        @SuppressLint("SetTextI18n")
        fun bind(result: FavCryptoModel) {
            b.cryptoName.text = " " + result.name
            b.cryptoSym.text = result.symbol
            b.cryptoPrice.text = result.price_usd + " $"

            val cryIcons: String = result.symbol.lowercase()
            Picasso.get().load(IMAGE_CRYPTO_URL + cryIcons).error(R.color.black)
                .into(b.cryptoIcons)
        }
    }

    internal class CryptoDiffUtil : DiffUtil.ItemCallback<FavCryptoModel>() {
        override fun areItemsTheSame(oldItem: FavCryptoModel, newItem: FavCryptoModel): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: FavCryptoModel, newItem: FavCryptoModel): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CryptoLyBinding.inflate(
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

        holder.b.diffLayout.isVisible = false
        holder.b.deleteFavCrypto.isVisible = true
//        holder.itemView.animation =
//            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)

    }

    private var onItemClickListener: ((FavCryptoModel) -> Unit)? = null

    fun setOnDeleteClickListener(listener: ((FavCryptoModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    private fun initClicks(holder: ItemHolder, itemData: FavCryptoModel) {
        holder.b.deleteFavCrypto.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

        holder.itemView.setOnClickListener {
            val action =
                FavCryptoFragmentDirections.actionFavCryptoFragmentToConvertCryptoFragment2(
                    CryptoModel(
                        itemData.symbol,
                        itemData.name,
                        itemData.rank,
                        itemData.price_usd,
                        "",
                        "",
                        "",
                        itemData.price_btc,
                        ""
                    ),
                    IMAGE_CRYPTO_URL,
                    false
                )
            it.findNavController().navigate(action)
        }

    }


}