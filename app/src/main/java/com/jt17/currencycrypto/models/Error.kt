package com.jt17.currencycrypto.models

data class Error(
    val status_code: Int = 0,
    val status_message: String? = null
)