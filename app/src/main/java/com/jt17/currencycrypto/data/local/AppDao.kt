package com.jt17.currencycrypto.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    /** For fetching data from api and save to room **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(data: List<CurrencyModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrypto(data: List<CryptoModel>)

    @Query("SELECT * FROM currency_data_table order by rate DESC")
    fun getAllCurrencyData(): List<CurrencyModel>?/* get all data from currency model */

    @Query("SELECT * FROM crypto_data_table order by cryptos_rank DESC")
    fun getAllCryptoData(): List<CryptoModel>?/* get all crypto data */

    @Delete
    fun deleteCrypto(cryData: List<CryptoModel>)/* delete a crypto from favourites */

    @Delete
    fun deleteCurrency(currData: List<CurrencyModel>)/* delete a currency from favourites */

    @Delete
    fun deleteCurrModel(currencyModel: CurrencyModel)

    @Query("SELECT * FROM currency_data_table WHERE country_code =:ccy")
    fun getCurrName(ccy: String?): Flow<CurrencyModel>

    /** For fetching data from currency and crypto entities and save to favourites entity room **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavCurrencies(favCurrencyModel: FavCurrencyModel)

    @Query("SELECT * FROM favourite_currencies_table")
    fun getAllFavCurrencies(): Flow<List<FavCurrencyModel>>

    @Query("SELECT * FROM favourite_currencies_table WHERE Ccy =:ccy")
    fun getFavCurrName(ccy: String?): Flow<FavCurrencyModel>

    @Query("DELETE FROM favourite_currencies_table")
    suspend fun deleteAllFavCurrencies()

    @Delete
    suspend fun deleteOneFavCurrency(favCurrencyModel: FavCurrencyModel)

    /***/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavCryptos(favCryptoModel: FavCryptoModel)

    @Query("SELECT * FROM favourite_crypto_table")
    fun getAllFavCryptos(): Flow<List<FavCryptoModel>>

    @Query("SELECT * FROM favourite_crypto_table WHERE Name =:name")
    fun getCryptoName(name: String?): Flow<FavCryptoModel>

    @Query("DELETE FROM favourite_crypto_table")
    suspend fun deleteAllFavCryptos()

    @Delete
    suspend fun deleteOneFavCrypto(favCryptoModel: FavCryptoModel)
    /********************************************/

}