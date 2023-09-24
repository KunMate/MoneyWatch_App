package com.pack.moneywatch_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShopListViewModelFactory (private val repository: ShopListRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShopListViewModel(repository) as T
    }
}
