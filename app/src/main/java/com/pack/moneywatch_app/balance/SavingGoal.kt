package com.pack.moneywatch_app.balance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings")
data class SavingGoal(

    @ColumnInfo(name = "itemName")
    var itemName: String,

    @ColumnInfo(name = "itemPrice")
    var itemPrice: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}