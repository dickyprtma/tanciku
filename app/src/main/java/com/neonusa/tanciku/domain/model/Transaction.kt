package com.neonusa.tanciku.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val id: Int,
    val description: String,
    val date: String,
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Int
)
