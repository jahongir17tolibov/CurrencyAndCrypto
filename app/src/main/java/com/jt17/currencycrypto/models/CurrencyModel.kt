package com.jt17.currencycrypto.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "currency_data_table")
data class CurrencyModel(
    @ColumnInfo(name = "country_code")
    var Ccy: String,

    @ColumnInfo(name = "cc_ru")
    var CcyNm_RU: String,

    @ColumnInfo(name = "cc_uz")
    var CcyNm_UZ: String,

    @ColumnInfo(name = "cc_uzc")
    var CcyNm_UZC: String,

    @ColumnInfo(name = "cc_en")
    var CcyNm_EN: String,

    @ColumnInfo(name = "rate")
    var Rate: String,

    @ColumnInfo(name = "differences")
    var Diff: String,

    @ColumnInfo(name = "date")
    var Date: String,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var Curid: Int
) : Parcelable