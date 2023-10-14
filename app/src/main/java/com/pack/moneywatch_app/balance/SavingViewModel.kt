package com.pack.moneywatch_app.balance

import android.provider.Settings.Global
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SavingViewModel(private val repository: SavingRepository) : ViewModel() {
    fun insert(items : SavingGoal) = GlobalScope.launch {
        repository.insert(items)
    }
    fun delete(items : SavingGoal) = GlobalScope.launch {
        repository.delete(items)
    }
    fun update(items : SavingGoal) = GlobalScope.launch {
        repository.update(items)
    }
    fun getAllItems() = repository.getAllSavings()
}