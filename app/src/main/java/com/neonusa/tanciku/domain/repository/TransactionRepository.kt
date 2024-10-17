package com.neonusa.tanciku.domain.repository

import com.neonusa.tanciku.domain.model.Transaction

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
}