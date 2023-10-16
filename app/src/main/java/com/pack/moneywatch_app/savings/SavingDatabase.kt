package com.pack.moneywatch_app.savings

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavingGoal::class], version = 1)
abstract class SavingDatabase : RoomDatabase() {

    abstract fun getSavingDao() : SavingDao

    companion object{
        @Volatile
        private var instance : SavingDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context)  = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }
    private fun createDatabase(context : Context) =
        Room.databaseBuilder(context.applicationContext, SavingDatabase::class.java, "saving.db").allowMainThreadQueries().build()
}
    }
