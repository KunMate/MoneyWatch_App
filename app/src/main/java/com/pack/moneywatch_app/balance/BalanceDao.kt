package com.pack.moneywatch_app.balance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BalanceDao {
    @Query("SELECT * FROM expenses WHERE NOT category = :categorySorted order by id desc")
    fun getAll(categorySorted: String = "egyéb"): List<BalanceTransaction>

    @Query("SELECT * FROM expenses WHERE category = :selectedCategory order by id desc")
    fun getCategoryType(selectedCategory : String) : List<BalanceTransaction>

    @Query("SELECT SUM(amount) FROM EXPENSES WHERE category = :saving" )
    fun getSavingBalance(saving: String = "Megtakarítás") : Int

    @Insert
    fun insertAll(vararg transaction: BalanceTransaction)

    @Delete
    fun delete(transaction: BalanceTransaction)

    @Update
    fun update(vararg transaction: BalanceTransaction)

}