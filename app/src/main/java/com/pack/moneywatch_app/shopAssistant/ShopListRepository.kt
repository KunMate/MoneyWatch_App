package com.pack.moneywatch_app.shopAssistant

import com.pack.moneywatch_app.shopAssistant.ShopListDatabase
import com.pack.moneywatch_app.shopAssistant.ShopListItems

class ShopListRepository(private val db: ShopListDatabase) {
    suspend fun insert(items: ShopListItems) = db.getShopListDao().insert(items)
    suspend fun delete(items: ShopListItems) = db.getShopListDao().delete(items)
    suspend fun update(items: ShopListItems) = db.getShopListDao().update(items)
    fun getTotalCost() = db.getShopListDao().getTotalCost()
    fun getAllItems() = db.getShopListDao().getAllShopItems()
    fun wipeCart() = db.getShopListDao().wipeCart()
    fun getItemsCount() = db.getShopListDao().getItemsCount()
}