package com.jt17.currencycrypto.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel

@Dao
interface UserDao {

    @Query("SELECT * FROM currency_data_table")
    fun getAllCurrencyData(): LiveData<List<CurrencyModel>>/* get all data from currency model */

    @Query("SELECT * FROM crypto_data_table")
    fun getAllCryptoData(): LiveData<List<CryptoModel>>/* get all crypto data */

    @Query("SELECT * FROM currency_data_table WHERE Cc_en =:cc_en")
    fun getCurrencyName(cc_en: String?): LiveData<CurrencyModel>/* get currency name */

    @Query("SELECT * FROM crypto_data_table WHERE Crypto_name =:crypto_name")
    fun getCryptoName(crypto_name: String?): LiveData<CryptoModel>/* get crypto name */

    @Query("DELETE FROM currency_data_table")
    suspend fun deleteAll()/* delete table(all data from table) */

    @Delete
    suspend fun deleteCrypto(cryptoModel: CryptoModel)/* delete a crypto from favourites */

    @Delete
    suspend fun deleteCurrency(currencyModel: CurrencyModel)/* delete a currency from favourites */

}