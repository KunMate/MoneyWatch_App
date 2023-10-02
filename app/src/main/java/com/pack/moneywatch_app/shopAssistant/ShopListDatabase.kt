package com.pack.moneywatch_app.shopAssistant

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopListItems::class], version = 1)
abstract class ShopListDatabase : RoomDatabase() {
    abstract fun getShopListDao(): ShopListDao

    companion object {
        @Volatile
        private var instance: ShopListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ShopListDatabase::class.java,
                "shoplist.db"
            ).allowMainThreadQueries().build()
    }
}