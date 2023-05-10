package com.jt17.currencycrypto.data.local

import androidx.room.*
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.models.FavCurrencyModel
import com.jt17.currencycrypto.utils.Constants.CRYPTO_TABLE_NAME
import com.jt17.currencycrypto.utils.Constants.CURRENCY_TABLE_NAME
import com.jt17.currencycrypto.utils.Constants.FAV_CRY_TABLE_NAME
import com.jt17.currencycrypto.utils.Constants.FAV_CURR_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    /** For fetching data from api and save to room **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(data: List<CurrencyModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrypto(data: List<CryptoModel>)

    @Query("SELECT * FROM $CURRENCY_TABLE_NAME")
    fun getAllCurrencyData(): List<CurrencyModel>?/* get all data from currency model */

    @Query("SELECT * FROM $CRYPTO_TABLE_NAME")
    fun getAllCryptoData(): List<CryptoModel>?/* get all crypto data */

    @Delete
    fun deleteCrypto(cryData: List<CryptoModel>)/* delete a crypto from favourites */

    @Delete
    fun deleteCurrency(currData: List<CurrencyModel>)/* delete a currency from favourites */

    @Query("DELETE FROM $CURRENCY_TABLE_NAME")
    fun clearAllCurrencies()

    @Query("DELETE FROM $CRYPTO_TABLE_NAME")
    fun clearAllCryptos()

    @Transaction
    fun deleteAndInsertCurrencies(data: List<CurrencyModel>) {
        clearAllCurrencies()
        insertCurrencies(data)
    }

    @Transaction
    fun deleteAndInsertCryptos(data: List<CryptoModel>) {
        clearAllCryptos()
        insertCrypto(data)
    }

    @Query("SELECT * FROM $CURRENCY_TABLE_NAME WHERE country_code =:ccy")
    fun getCurrName(ccy: String?): Flow<CurrencyModel>?

    @Query("SELECT * FROM $CRYPTO_TABLE_NAME WHERE crypto_name =:name")
    fun getCryName(name: String?): Flow<CryptoModel>?

    /** For fetching data from currency and crypto entities and save to favourites entity room **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavCurrencies(favCurrencyModel: FavCurrencyModel)

    @Query("SELECT * FROM $FAV_CURR_TABLE_NAME")
    fun getAllFavCurrencies(): Flow<List<FavCurrencyModel>>

    @Query("SELECT * FROM $FAV_CURR_TABLE_NAME WHERE Ccy =:ccy")
    fun getFavCurrName(ccy: String?): Flow<FavCurrencyModel>

    @Query("DELETE FROM $FAV_CURR_TABLE_NAME")
    suspend fun deleteAllFavCurrencies()

    @Delete
    suspend fun deleteOneFavCurrency(favCurrencyModel: FavCurrencyModel)

    @Update
    suspend fun upDateFavCurrency(favCurrencyModel: FavCurrencyModel)

    /***/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavCryptos(favCryptoModel: FavCryptoModel)

    @Query("SELECT * FROM $FAV_CRY_TABLE_NAME")
    fun getAllFavCryptos(): Flow<List<FavCryptoModel>>

    @Query("SELECT * FROM $FAV_CRY_TABLE_NAME WHERE Name =:name")
    fun getCryptoName(name: String?): Flow<FavCryptoModel>

    @Query("DELETE FROM $FAV_CRY_TABLE_NAME")
    suspend fun deleteAllFavCryptos()

    @Delete
    suspend fun deleteOneFavCrypto(favCryptoModel: FavCryptoModel)

    @Update
    suspend fun upDateFavCrypto(favCryptoModel: FavCryptoModel)
    /********************************************/

}