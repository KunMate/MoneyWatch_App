package com.pack.moneywatch_app.shopAssistant

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShopListViewModel(private val repository: ShopListRepository) : ViewModel() {
    fun insert(items: ShopListItems) = GlobalScope.launch {
        repository.insert(items)
    }
    fun delete(items: ShopListItems) = GlobalScope.launch {
        repository.delete(items)
    }
    fun getAllShopListItems() = repository.getAllItems()
    fun getTotalCost() = repository.getTotalCost()
}