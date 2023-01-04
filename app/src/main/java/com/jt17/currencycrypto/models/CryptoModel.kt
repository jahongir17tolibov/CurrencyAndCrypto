package com.jt17.currencycrypto.models

data class CryptoModel(
    val symbol: String,
    val name: String,
    val rank: String,
    val price_usd: String,
    val percent_change_24h: String,
    val percent_change_1h: String,
    val percent_change_7d: String,
    val price_btc: String,
    val market_cap_usd: String
)
