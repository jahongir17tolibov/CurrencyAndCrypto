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
    val Ccy: String,

    @ColumnInfo(name = "cc_ru")
    val CcyNm_RU: String,

    @ColumnInfo(name = "cc_uz")
    val CcyNm_UZ: String,

    @ColumnInfo(name = "cc_uzc")
    val CcyNm_UZC: String,

    @ColumnInfo(name = "cc_en")
    val CcyNm_EN: String,

    @ColumnInfo(name = "rate")
    val Rate: String,

    @ColumnInfo(name = "differences")
    val Diff: String
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

}

//@Parcelize
//data class ChildModel(
//    val iteModel: CurrencyModel,
//    val img: String
//) : Parcelable
