package com.jt17.currencycrypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jt17.currencycrypto.data.Converts
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel
import com.jt17.currencycrypto.models.FavCryptoModel
import com.jt17.currencycrypto.models.FavCurrencyModel

@Database(
    entities = [CurrencyModel::class, CryptoModel::class, FavCurrencyModel::class, FavCryptoModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converts::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): AppDao

}

