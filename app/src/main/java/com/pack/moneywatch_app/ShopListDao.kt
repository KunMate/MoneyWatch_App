package com.pack.moneywatch_app

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShopListItems)

    @Delete
    suspend fun delete(item: ShopListItems)

    @Query("SELECT * FROM shoplist_items")
    fun getAllShopItems() : LiveData<List<ShopListItems>>

}