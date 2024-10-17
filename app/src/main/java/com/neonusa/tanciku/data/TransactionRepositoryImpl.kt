package com.neonusa.tanciku.data

import com.neonusa.tanciku.data.local.TransactionDao
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val transactionDao: TransactionDao): TransactionRepository {
    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }
}