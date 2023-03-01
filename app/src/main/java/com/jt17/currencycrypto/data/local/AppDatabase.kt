package com.jt17.currencycrypto.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jt17.currencycrypto.data.Converts
import com.jt17.currencycrypto.models.CryptoModel
import com.jt17.currencycrypto.models.CurrencyModel

@Database(
    entities = [CurrencyModel::class, CryptoModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converts::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): AppDao

//    companion object {
//
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabaseClient(context: Context): AppDatabase {
//            if (INSTANCE != null) return INSTANCE!!
//
//            synchronized(this) {
//                INSTANCE = Room
//                    .databaseBuilder(context, AppDatabase::class.java, "CURRENCY_CRYPTO_DATABASE")
//                    .fallbackToDestructiveMigration()
//                    .build()
//
//                return INSTANCE!!
//
//            }
//        }
//
//    }

}