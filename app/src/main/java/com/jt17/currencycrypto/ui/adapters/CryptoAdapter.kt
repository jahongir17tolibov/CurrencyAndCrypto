package com.jt17.currencycrypto.ui.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.CryptoLyBinding
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.ui.screens.CryptoFragmentDirections
import com.squareup.picasso.Picasso

class CryptoAdapter : ListAdapter<CryptoModel, CryptoAdapter.ItemHolder>(CryptoDiffUtil()) {

    companion object {
        const val IMAGE_CRYPTO_URL = "https://coinicons-api.vercel.app/api/icon/"
    }

    private val itemVisibilityMap = HashMap<Int, Boolean>().apply {
        for (i in 0 until itemCount) {
            put(i, false)
        }
    }

    inner class ItemHolder(val b: CryptoLyBinding) : RecyclerView.ViewHolder(b.root) {

        @SuppressLint("SetTextI18n")
        fun bind(result: CryptoModel) {
            b.cryptoName.text = " " + result.name
            b.cryptoSym.text = result.symbol
            b.cryptoRank.text = result.rank + ". "
            b.cryptoPrice.text = result.price_usd + " $"
            b.diffStatus1h.text = result.percent_change_1h
            b.diffStatus24h.text = result.percent_change_24h
            b.diffStatus7d.text = result.percent_change_7d

            val cryIcons: String = result.symbol.lowercase()
            Log.d("hmmmm2", "bind: $cryIcons")
            Picasso.get().load(IMAGE_CRYPTO_URL + cryIcons).error(R.color.black)
                .into(b.cryptoIcons)

        }

    }

    internal class CryptoDiffUtil : DiffUtil.ItemCallback<CryptoModel>() {
        override fun areItemsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean {
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
        val itemVis = itemVisibilityMap[position] ?: false

        holder.bind(itemData)

        statusVisiblities1h(holder)
        statusVisiblities24h(holder)
        statusVisiblities7d(holder)

        holder.b.bottomHidedLl.isVisible = itemVis
        initClicks(holder, itemData, position)

        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)

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

    private var onItemClickListener: ((CryptoModel) -> Unit)? = null

    fun setOnFavItemClickListener(listener: ((CryptoModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    private fun initClicks(holder: ItemHolder, itemData: CryptoModel, pos: Int) {
        holder.itemView.setOnClickListener {
            val currentVis = itemVisibilityMap[pos] ?: false
            itemVisibilityMap[pos] = !currentVis
            notifyItemChanged(pos)
        }

        holder.b.beginToConvert.setOnClickListener {
            val navController = it.findNavController()
            val action = CryptoFragmentDirections.actionCryptoFragmentToConvertCryptoFragment2(
                itemData,
                IMAGE_CRYPTO_URL
            )
            navController.navigate(action)
        }
        val rotAnim = AlphaAnimation(0.0F, 1.0F)
        rotAnim.duration = 1000
        rotAnim.interpolator = BounceInterpolator()
        holder.b.addToFav.setOnClickListener {

            onItemClickListener?.invoke(itemData)

            if (holder.b.starNotAdd.isVisible) {
                holder.b.starNotAdd.isVisible = false
                holder.b.starAdded.run {
                    isVisible = true
                    startAnimation(rotAnim)
                }
            } else {
                holder.b.starAdded.isVisible = false
                holder.b.starNotAdd.run {
                    isVisible = true
                    startAnimation(rotAnim)
                }
            }
        }

    }

}