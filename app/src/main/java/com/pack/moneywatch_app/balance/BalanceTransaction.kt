package com.pack.moneywatch_app.balance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class BalanceTransaction(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val description : String,
    val amount: Int,
    val category: String,
    val expinfo: String) : java.io.Serializable
