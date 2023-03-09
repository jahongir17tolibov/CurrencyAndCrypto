package com.jt17.currencycrypto.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("favourite_currencies_table")
data class FavCurrencyModel(
    @ColumnInfo("Ccy")
    var CountryCode: String,
    @ColumnInfo("CcyNm_EN")
    var CurrencyName_ENG: String,
    @ColumnInfo("Rate")
    var Rate: String,
    @ColumnInfo("CcyNm_RU")
    var CurrencyName_RU: String,
    @ColumnInfo("CcyNm_UZ")
    var CurrencyName_UZ: String,
    @ColumnInfo("CcyNm_UZC")
    var CurrencyName_UZK: String,
    @ColumnInfo("Diff")
    var Diff: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int
) : Parcelable