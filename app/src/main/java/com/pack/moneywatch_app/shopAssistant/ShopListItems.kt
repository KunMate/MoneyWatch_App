package com.pack.moneywatch_app.shopAssistant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoplist_items")
data class ShopListItems (

    @ColumnInfo(name = "itemName")
    var itemName: String,

    @ColumnInfo(name = "itemAmount")
    var itemAmount: Float,

    @ColumnInfo(name = "itemPrice")
    var itemPrice: Int,
){
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}