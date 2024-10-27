package com.neonusa.tanciku.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    // paged
    @Query("SELECT * FROM transactions LIMIT :pageSize OFFSET :offset")
    suspend fun getTransactionsForPage(pageSize: Int, offset: Int): List<Transaction>

    @Query("""
    SELECT * FROM transactions 
    WHERE strftime('%Y-%m', date) = strftime('%Y-%m', 'now') 
    ORDER BY date DESC 
    LIMIT 4
""")
    fun getCurrentMonthLatestTransactions(): Flow<List<Transaction>>

    /* IMPORTANT NOTE
        di AddTransactionScreen tanggal satu digit seperti 1,2,3..etc diubah formatnya menjadi 01,02,03..etc
        hal ini karena tanggal fungsi strftime milik sqlite menggunakan format YYYY-MM-DD example :
        SELECT strftime('%Y', '2024-10-10') AS year; -> return 2024
        SELECT strftime('%Y', '2024-10-9') AS year; -> return null
        SELECT strftime('%Y', '2024-10-09') AS year; -> return 2024
     */

    // Total income & Total expenses for the current month
    @Query("""
        SELECT SUM(amount) FROM transactions 
        WHERE type = 'Pemasukan' AND strftime('%Y-%m', date) = strftime('%Y-%m', 'now')
    """)
    suspend fun getCurrentMonthTotalIncome(): Int
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

    // Fungsi untuk memperbarui data transaction
    @Update
    suspend fun update(transaction: Transaction)
}