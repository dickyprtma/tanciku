package com.neonusa.tanciku.domain.repository

import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun deleteTransactionById(id : Int)

    suspend fun getCurrentMonthTotalIncome(): Int
    suspend fun getCurrentMonthTotalExpense(): Int
    suspend fun getCurentMonthTotalTransactionByCategory(category: TransactionCategory): Int //todo: fix this typo

    fun getCurrentMonthLatestTransactions(): Flow<List<Transaction>>
}