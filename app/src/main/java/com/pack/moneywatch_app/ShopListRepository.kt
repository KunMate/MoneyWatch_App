package com.pack.moneywatch_app

class ShopListRepository(private val db: ShopListDatabase) {
    suspend fun insert(items: ShopListItems) = db.getShopListDao().insert(items)
    suspend fun delete(items: ShopListItems) = db.getShopListDao().delete(items)
    fun getAllItems() = db.getShopListDao().getAllShopItems()
}