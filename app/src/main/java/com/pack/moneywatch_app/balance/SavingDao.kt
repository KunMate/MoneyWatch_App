package com.pack.moneywatch_app.balance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SavingDao {
    @Query("SELECT * FROM savings")
    fun getAllSavings() : LiveData<List<SavingGoal>>
    @Insert
    fun insert(vararg savingGoal: SavingGoal)

    @Delete
    fun delete(savingGoal: SavingGoal)

    @Update
    fun update(vararg savingGoal: SavingGoal)
}