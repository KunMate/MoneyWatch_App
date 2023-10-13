package com.pack.moneywatch_app.shopAssistant

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(item: ShopListItems)

    @Delete
    suspend fun delete(item: ShopListItems)

    @Query("SELECT * FROM shoplist_items")
    fun getAllShopItems() : LiveData<List<ShopListItems>>

    @Query("SELECT SUM (itemAmount * itemPrice) FROM shoplist_items")
    fun getTotalCost() : String

    @Query("DELETE FROM shoplist_items")
    fun wipeCart()

    @Query("SELECT COUNT(id) from shoplist_items")
    fun getItemsCount() : Int

}