package com.pack.moneywatch_app.balance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pack.moneywatch_app.shopAssistant.ShopListDatabase

@Database(entities = [BalanceTransaction::class], version = 1)
abstract class BalanceDatabase : RoomDatabase() {
    abstract fun transactionDao() : BalanceDao

    companion object {
        @Volatile
        private var instance: BalanceDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BalanceDatabase::class.java,
                "expenses.db"
            ).allowMainThreadQueries().build()
    }
}