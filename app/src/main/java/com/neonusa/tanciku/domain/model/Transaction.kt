package com.neonusa.tanciku.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val description: String,
    val date: String,
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Int
):Parcelable
