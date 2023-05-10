package com.jt17.currencycrypto.ui.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.data.sharedPref.AppPreference
import com.jt17.currencycrypto.databinding.CurrencyLyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.ui.screens.CurrencyFragmentDirections
import com.jt17.currencycrypto.utils.Constants.IMAGE_URL
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.HashMap

class CurrencyAdapter : ListAdapter<CurrencyModel, CurrencyAdapter.ItemHolder>(CurrencyDiffUtil()) {
    //icon source link: https://flagpedia.net/download/api

    private val itemVisibilityMap = HashMap<Int, Boolean>().apply {
        for (i in 0 until itemCount) {
            put(i, false)
        }
    }
    private var onItemClickListener: ((CurrencyModel) -> Unit)? = null

    fun setOnFavItemClickListener(listener: ((CurrencyModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    inner class ItemHolder(val b: CurrencyLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: CurrencyModel) {
            b.pricer.text = result.Rate

            when (AppPreference.getInstance().getAppsLang()) {
                "uz" -> b.currName.text = result.CcyNm_UZ
                "ru" -> b.currName.text = result.CcyNm_RU
                "en" -> b.currName.text = result.CcyNm_EN
                else -> b.currName.text = result.CcyNm_EN
            }

            b.country3str.text = result.Ccy
            b.diffStatus.text = result.Diff

            val flags: String = result.Ccy.take(2).lowercase()

            Picasso.get().load("${IMAGE_URL + flags}.png")
                .placeholder(R.color.md_theme_dark_outline)
                .error(R.color.black)
                .into(b.flagAvatars)

            AppPreference.getInstance().setDate(result.Date)
        }

    }

    internal class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyModel>() {
        override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
            return oldItem.CcyNm_EN == newItem.CcyNm_EN
        }

        override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CurrencyLyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = getItem(position)
        val itemVis = itemVisibilityMap[position] ?: false

        holder.bind(itemData)
        diffVisibilities(holder)
        holder.b.bottomHidedLl.isVisible = itemVis
        initClicks(holder, itemData, position)

        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)

    }

    @SuppressLint("ResourceAsColor")
    private fun diffVisibilities(holder: ItemHolder) {
        val diffValue = holder.b.diffStatus.text.toString()
        if (diffValue.toDouble() > 0) {
            holder.b.diffCardView.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0ADE00"))
            holder.b.diffImgDOWN.isVisible = false
            holder.b.diffImgNONE.isVisible = false
            holder.b.diffImgUP.isVisible = true
            holder.b.diffTxtPLUS.isVisible = true
        } else if (diffValue.toDouble() < 0) {
            holder.b.diffCardView.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0101"))
            holder.b.diffImgDOWN.isVisible = true
            holder.b.diffImgNONE.isVisible = false
            holder.b.diffImgUP.isVisible = false
            holder.b.diffTxtPLUS.isVisible = false
        } else {
            holder.b.diffCardView.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#888888"))
            holder.b.diffImgDOWN.isVisible = false
            holder.b.diffImgNONE.isVisible = true
            holder.b.diffImgUP.isVisible = false
            holder.b.diffTxtPLUS.isVisible = false
        }
    }

    private fun initClicks(holder: ItemHolder, itemData: CurrencyModel, pos: Int) {
        holder.itemView.setOnClickListener {
            val currentVis = itemVisibilityMap[pos] ?: false
            itemVisibilityMap[pos] = !currentVis
            notifyItemChanged(pos)
        }

        holder.b.beginToConvert.setOnClickListener {
            val navController = it.findNavController()
            val action = CurrencyFragmentDirections.actionCurrencyFragmentToConverterFragment(
                itemData,
                IMAGE_URL,
                true
            )
            navController.navigate(action)

        }
        holder.b.addToFav.setOnClickListener {

            onItemClickListener?.invoke(itemData)

            if (holder.b.starNotAddCurr.isVisible) {
                holder.b.starNotAddCurr.isVisible = false
                holder.b.starAddedCurr.apply {
                    isVisible = true
                    startAnimation(starAnimation())
                }
            } else {
                holder.b.starAddedCurr.isVisible = false
                holder.b.starNotAddCurr.apply {
                    isVisible = true
                    startAnimation(starAnimation())
                }
            }

        }
    }

    private fun starAnimation(): Animation {
        val rotAnim = AlphaAnimation(0.0F, 1.0F)
        return rotAnim.apply {
            duration = 1000
            interpolator = BounceInterpolator()
        }
    }

}