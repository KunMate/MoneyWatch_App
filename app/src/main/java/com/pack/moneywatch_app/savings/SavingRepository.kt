package com.pack.moneywatch_app.savings

class SavingRepository(private val db: SavingDatabase) {
    fun insert(items : SavingGoal) = db.getSavingDao().insert(items)
    fun delete(items : SavingGoal) = db.getSavingDao().delete(items)
    fun update(items : SavingGoal) = db.getSavingDao().update(items)
    fun getAllSavings() = db.getSavingDao().getAllSavings()
}