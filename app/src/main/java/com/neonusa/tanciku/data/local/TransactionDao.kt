package com.neonusa.tanciku.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neonusa.tanciku.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("DELETE FROM `transactions` WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM transactions")
    fun getTransactions(): Flow<List<Transaction>>

    //todo :
    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Pemasukan'")
    suspend fun getTotalIncome(): Int

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Pengeluaran'")
    suspend fun getTotalExpense(): Int
}