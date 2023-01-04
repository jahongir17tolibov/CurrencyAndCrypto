package com.jt17.currencycrypto.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.CryptoLyBinding
import com.jt17.currencycrypto.models.CryptoModel
import com.squareup.picasso.Picasso

object Constants1 {

    const val IMAGE_CRYPTO_URL = "https://coinicons-api.vercel.app/api/icon/"

}

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.ItemHolder>() {

    inner class ItemHolder(val b: CryptoLyBinding) : RecyclerView.ViewHolder(b.root) {

        @SuppressLint("SetTextI18n")
        fun bind(result: CryptoModel) {
            b.cryptoName.text = " " + result.name
            b.cryptoSym.text = result.symbol
            b.cryptoRank.text = result.rank
            b.cryptoPrice.text = result.price_usd
            b.diffStatus1h.text = result.percent_change_1h
            b.diffStatus24h.text = result.percent_change_24h
            b.diffStatus7d.text = result.percent_change_7d

            val cryIcons: String = result.symbol.lowercase()
            Picasso.get().load(Constants1.IMAGE_CRYPTO_URL + cryIcons).error(R.color.black)
                .into(b.cryptoIcons)

        }

    }

    private var baseList: List<CryptoModel> = emptyList()

    fun newList(list: List<CryptoModel>) {
        baseList = list
        notifyDataSetChanged()
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
        val itemData = baseList[position]

        holder.bind(itemData)

        statusVisiblities1h(holder)
        statusVisiblities24h(holder)
        statusVisiblities7d(holder)

    }

    private fun statusVisiblities1h(holder: ItemHolder) {
        val diffValue = holder.b.diffStatus1h.text.toString()
        if (diffValue.toDouble() > 0) {
            holder.b.CryptoDiffCardview1h.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0ADE00"))
            holder.b.diffImgDOWN1h.isVisible = false
            holder.b.diffImgNONE1h.isVisible = false
            holder.b.diffImgUP1h.isVisible = true
            holder.b.diffTxtPLUS1h.isVisible = true
        } else if (diffValue.toDouble() < 0) {
            holder.b.CryptoDiffCardview1h.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0101"))
            holder.b.diffImgDOWN1h.isVisible = true
            holder.b.diffImgNONE1h.isVisible = false
            holder.b.diffImgUP1h.isVisible = false
            holder.b.diffTxtPLUS1h.isVisible = false
        } else {
            holder.b.CryptoDiffCardview1h.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#888888"))
            holder.b.diffImgDOWN1h.isVisible = false
            holder.b.diffImgNONE1h.isVisible = true
            holder.b.diffImgUP1h.isVisible = false
            holder.b.diffTxtPLUS1h.isVisible = false
        }
    }

    private fun statusVisiblities24h(holder: ItemHolder) {
        val diffValue = holder.b.diffStatus24h.text.toString()
        if (diffValue.toDouble() > 0) {
            holder.b.CryptoDiffCardview24h.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0ADE00"))
            holder.b.diffImgDOWN24h.isVisible = false
            holder.b.diffImgNONE24h.isVisible = false
            holder.b.diffImgUP24h.isVisible = true
            holder.b.diffTxtPLUS24h.isVisible = true
        } else if (diffValue.toDouble() < 0) {
            holder.b.CryptoDiffCardview24h.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0101"))
            holder.b.diffImgDOWN24h.isVisible = true
            holder.b.diffImgNONE24h.isVisible = false
            holder.b.diffImgUP24h.isVisible = false
            holder.b.diffTxtPLUS24h.isVisible = false
        } else {
            holder.b.CryptoDiffCardview24h.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#888888"))
            holder.b.diffImgDOWN24h.isVisible = false
            holder.b.diffImgNONE24h.isVisible = true
            holder.b.diffImgUP24h.isVisible = false
            holder.b.diffTxtPLUS24h.isVisible = false
        }
    }

    private fun statusVisiblities7d(holder: ItemHolder) {
        val diffValue = holder.b.diffStatus7d.text.toString()
        if (diffValue.toDouble() > 0) {
            holder.b.CryptoDiffCardview7d.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0ADE00"))
            holder.b.diffImgDOWN7d.isVisible = false
            holder.b.diffImgNONE7d.isVisible = false
            holder.b.diffImgUP7d.isVisible = true
            holder.b.diffTxtPLUS7d.isVisible = true
        } else if (diffValue.toDouble() < 0) {
            holder.b.CryptoDiffCardview7d.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0101"))
            holder.b.diffImgDOWN7d.isVisible = true
            holder.b.diffImgNONE7d.isVisible = false
            holder.b.diffImgUP7d.isVisible = false
            holder.b.diffTxtPLUS7d.isVisible = false
        } else {
            holder.b.CryptoDiffCardview7d.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#888888"))
            holder.b.diffImgDOWN7d.isVisible = false
            holder.b.diffImgNONE7d.isVisible = true
            holder.b.diffImgUP7d.isVisible = false
            holder.b.diffTxtPLUS7d.isVisible = false
        }
    }

    override fun getItemCount(): Int = baseList.size

}