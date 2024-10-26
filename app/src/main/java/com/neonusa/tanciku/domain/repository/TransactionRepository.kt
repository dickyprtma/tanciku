package com.neonusa.tanciku.domain.repository

import androidx.paging.PagingData
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun deleteTransactionById(id : Int)

    fun getTransactions(): Flow<PagingData<Transaction>>

    suspend fun getCurrentMonthTotalIncome(): Int
    suspend fun getCurrentMonthTotalExpense(): Int
    suspend fun getCurrentMonthTotalTransactionByCategory(category: TransactionCategory): Int

    fun getCurrentMonthLatestTransactions(): Flow<List<Transaction>>
    suspend fun updateTransaction(transaction: Transaction)
}