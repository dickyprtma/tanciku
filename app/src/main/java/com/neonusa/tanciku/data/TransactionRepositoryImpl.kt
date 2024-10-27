package com.neonusa.tanciku.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.neonusa.tanciku.data.local.TransactionDao
import com.neonusa.tanciku.data.local.TransactionPagingSource
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(private val transactionDao: TransactionDao): TransactionRepository {
    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    override suspend fun deleteTransactionById(id: Int) {
        transactionDao.deleteById(id)
    }

    override suspend fun getCurrentMonthTotalIncome(): Int {
        return transactionDao.getCurrentMonthTotalIncome()
    }

    override suspend fun getCurrentMonthTotalExpense(): Int {
        return transactionDao.getCurrentMonthTotalExpense()
    }

    override suspend fun getCurrentMonthTotalTransactionByCategory(category: TransactionCategory): Int {
        return transactionDao.getCurrentMonthTotalTransactionByCategory(category)
    }

    override fun getTransactions(): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                TransactionPagingSource(transactionDao)
            }
        ).flow
    }

    override fun getCurrentMonthLatestTransactions(): Flow<List<Transaction>> {
        return transactionDao.getCurrentMonthLatestTransactions()
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction)
    }

}