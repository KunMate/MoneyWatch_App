package com.pack.moneywatch_app.balance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BalanceDao {
    @Query("SELECT * FROM expenses order by id desc")
    fun getAll(): List<BalanceTransaction>

    @Query("SELECT * FROM expenses WHERE category = :selectedCategory order by id desc")
    fun getCategoryType(selectedCategory : String) : List<BalanceTransaction>

    @Insert
    fun insertAll(vararg transaction: BalanceTransaction)

    @Delete
    fun delete(transaction: BalanceTransaction)

    @Update
    fun update(vararg transaction: BalanceTransaction)
}