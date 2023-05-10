package com.jt17.currencycrypto.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jt17.currencycrypto.utils.Constants.FAV_CRY_TABLE_NAME
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FAV_CRY_TABLE_NAME)
data class FavCryptoModel(
    @ColumnInfo("Name")
    var name: String,
    @ColumnInfo("Symbol")
    var symbol: String,
    @ColumnInfo("Ranking")
    var rank: String,
    @ColumnInfo("PriceInUSD")
    var price_usd: String,
    @ColumnInfo("PriceInBTC")
    var price_btc: String

) : Parcelable {
    @IgnoredOnParcel
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
