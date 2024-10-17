package com.neonusa.tanciku.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neonusa.tanciku.domain.model.Transaction

@Database(entities = [Transaction::class],version = 1)
@TypeConverters(TransactionConverter::class)
abstract class TransactionDatabase: RoomDatabase() {
    abstract val transactionDao: TransactionDao
}