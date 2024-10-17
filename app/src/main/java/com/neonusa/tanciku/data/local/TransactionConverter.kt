package com.neonusa.tanciku.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType

@ProvidedTypeConverter
class TransactionConverter {
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(type: String): TransactionType = TransactionType.valueOf(type)

    @TypeConverter
    fun fromTransactionCategory(category: TransactionCategory): String = category.name

    @TypeConverter
    fun toTransactionCategory(category: String): TransactionCategory = TransactionCategory.valueOf(category)
}