package com.jt17.currencycrypto.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.CurrencyLyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.squareup.picasso.Picasso

object Constants {
    //icon source link: https://flagpedia.net/download/api
    const val IMAGE_URL = "https://flagcdn.com/w160/"
}

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ItemHolder>() {

    inner class ItemHolder(val b: CurrencyLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: CurrencyModel) {

            b.pricer.text = result.Rate
            b.currName.text = result.CcyNm_EN
            b.country3str.text = result.Ccy
            b.diffStatus.text = result.Diff

            val flags: String = result.Ccy.take(2).lowercase()

            Picasso.get().load("${Constants.IMAGE_URL + flags}.png")
                .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
                .into(b.flagAvatars)

        }

    }

    var baseList: List<CurrencyModel> = emptyList()

    fun newList(list: List<CurrencyModel>) {
        baseList = list
        notifyDataSetChanged()
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
        val itemData = baseList[position]

        holder.bind(itemData)
        diffVisiblities(holder)

    }

    override fun getItemCount(): Int = baseList.size

    @SuppressLint("ResourceAsColor")
    private fun diffVisiblities(holder: ItemHolder) {
        val diffValue = holder.b.diffStatus.text.toString()
        if (diffValue.toDouble() > 0) {
            holder.b.DiffCardview.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0ADE00"))
            holder.b.diffImgDOWN.isVisible = false
            holder.b.diffImgNONE.isVisible = false
            holder.b.diffImgUP.isVisible = true
            holder.b.diffTxtPLUS.isVisible = true
        } else if (diffValue.toDouble() < 0) {
            holder.b.DiffCardview.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0101"))
            holder.b.diffImgDOWN.isVisible = true
            holder.b.diffImgNONE.isVisible = false
            holder.b.diffImgUP.isVisible = false
            holder.b.diffTxtPLUS.isVisible = false
        } else {
            holder.b.DiffCardview.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#888888"))
            holder.b.diffImgDOWN.isVisible = false
            holder.b.diffImgNONE.isVisible = true
            holder.b.diffImgUP.isVisible = false
            holder.b.diffTxtPLUS.isVisible = false
        }
    }

}