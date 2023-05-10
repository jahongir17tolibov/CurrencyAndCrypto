package com.jt17.currencycrypto.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jt17.currencycrypto.utils.Constants.CRYPTO_TABLE_NAME
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = CRYPTO_TABLE_NAME)
data class CryptoModel(
    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "crypto_name")
    val name: String,

    @ColumnInfo(name = "cryptos_rank")
    val rank: String,

    @ColumnInfo(name = "price_usd")
    val price_usd: String,

    @ColumnInfo(name = "pc24h")
    val percent_change_24h: String,

    @ColumnInfo(name = "pc1h")
    val percent_change_1h: String,

    @ColumnInfo(name = "pc7d")
    val percent_change_7d: String,

    @ColumnInfo(name = "price_btc")
    val price_btc: String,

    @ColumnInfo(name = "market_cap_usd")
    val market_cap_usd: String

) : Parcelable {

    @IgnoredOnParcel
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var myID: Int? = null
}
