package com.jt17.currencycrypto.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(data: List<CurrencyModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrypto(data: List<CryptoModel>)

    @Query("SELECT * FROM currency_data_table order by country_code DESC")
    fun getAllCurrencyData(): List<CurrencyModel>?/* get all data from currency model */

    @Query("SELECT * FROM crypto_data_table order by cryptos_rank DESC")
    fun getAllCryptoData(): List<CryptoModel>?/* get all crypto data */

    @Query("SELECT * FROM currency_data_table WHERE Cc_en =:cc_en")
    fun getCurrencyName(cc_en: String?): LiveData<CurrencyModel>/* get currency name */

    @Query("SELECT * FROM crypto_data_table WHERE Crypto_name =:crypto_name")
    fun getCryptoName(crypto_name: String?): LiveData<CryptoModel>/* get crypto name */

    @Delete
    fun deleteCrypto(cryData: List<CryptoModel>)/* delete a crypto from favourites */

    @Delete
    fun deleteCurrency(currData: List<CurrencyModel>)/* delete a currency from favourites */

}