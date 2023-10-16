package com.pack.moneywatch_app.savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SavingViewModelFactory(private val repository: SavingRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass : Class<T>):T {
        return SavingViewModel(repository) as T
    }
}