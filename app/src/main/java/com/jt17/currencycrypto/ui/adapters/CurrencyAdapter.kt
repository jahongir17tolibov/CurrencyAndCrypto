package com.jt17.currencycrypto.ui.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jt17.currencycrypto.R
import com.jt17.currencycrypto.databinding.CurrencyLyBinding
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.ui.fragments.CurrencyFragmentDirections
import com.squareup.picasso.Picasso
import java.util.*


class CurrencyAdapter : ListAdapter<CurrencyModel, CurrencyAdapter.ItemHolder>(CurrencyDiffUtil()) {
    companion object {
        //icon source link: https://flagpedia.net/download/api
        const val IMAGE_URL = "https://flagcdn.com/w160/"
    }

    private var onItemClickListener: ((CurrencyModel) -> Unit)? = null

    fun setOnFavItemClickListener(listener: ((CurrencyModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    inner class ItemHolder(val b: CurrencyLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: CurrencyModel) {

            b.pricer.text = result.Rate
            b.currName.text = result.CcyNm_EN
            b.country3str.text = result.Ccy
            b.diffStatus.text = result.Diff

            val flags: String = result.Ccy.take(2).lowercase()

            Picasso.get().load("${IMAGE_URL + flags}.png")
                .placeholder(R.drawable.ic_launcher_background).error(R.color.black)
                .into(b.flagAvatars)

        }

    }

    internal class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyModel>() {
        override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
            return newItem.CcyNm_EN == oldItem.CcyNm_EN
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

        holder.bind(itemData)
        diffVisiblities(holder)
        initClicks(holder, itemData)

        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)


    }

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

    private fun initClicks(holder: ItemHolder, itemData: CurrencyModel) {
        holder.itemView.setOnClickListener {
            if (!holder.b.bottomHidedLl.isVisible) {
                holder.b.bottomHidedLl.visibility = View.VISIBLE
            } else {
                holder.b.bottomHidedLl.visibility = View.GONE
            }
        }

        holder.b.beginToConvert.setOnClickListener {
            val navController = it.findNavController()
            val action = CurrencyFragmentDirections.actionCurrencyFragmentToConverterFragment(
                itemData,
                IMAGE_URL
            )
            navController.navigate(action)

        }

        holder.b.addToFav.setOnClickListener {

            onItemClickListener?.invoke(itemData)

            val rotAnim = AlphaAnimation(0.0F, 1.0F)
            rotAnim.duration = 1000
            rotAnim.interpolator = BounceInterpolator()

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