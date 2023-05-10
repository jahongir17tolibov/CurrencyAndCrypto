package com.jt17.currencycrypto.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jt17.currencycrypto.utils.Constants.FAV_CURR_TABLE_NAME
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FAV_CURR_TABLE_NAME)
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
    var Diff: String

) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}