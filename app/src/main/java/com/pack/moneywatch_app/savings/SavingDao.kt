package com.pack.moneywatch_app.savings

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pack.moneywatch_app.savings.SavingGoal

@Dao
interface SavingDao {
    @Query("SELECT * FROM savings ORDER BY itemPrice desc")
    fun getAllSavings() : LiveData<List<SavingGoal>>
    @Insert
    fun insert(vararg savingGoal: SavingGoal)

    @Delete
    fun delete(savingGoal: SavingGoal)

    @Update
    fun update(vararg savingGoal: SavingGoal)

}