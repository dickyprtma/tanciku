package com.neonusa.tanciku.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
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

    // Total income & Total expenses for the current month
    @Query("""
        SELECT SUM(amount) FROM transactions 
        WHERE type = 'Pemasukan' AND strftime('%Y-%m', date) = strftime('%Y-%m', 'now')
    """)
    suspend fun getCurrentMonthTotalIncome(): Int
    //
    @Query("""
        SELECT SUM(amount) FROM transactions 
        WHERE type = 'Pengeluaran' AND strftime('%Y-%m', date) = strftime('%Y-%m', 'now')
    """)
    suspend fun getCurrentMonthTotalExpense(): Int

    // Total transactions for a specific category in the current month
    @Query("""
        SELECT SUM(amount) FROM transactions 
        WHERE category = :category AND strftime('%Y-%m', date) = strftime('%Y-%m', 'now')
    """)
    suspend fun getCurrentMonthTotalTransactionByCategory(category: TransactionCategory): Int
}