package com.neonusa.tanciku.domain.usecases.transaction

import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentMonthLatestTransactions(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>>{
        return transactionRepository.getCurrentMonthLatestTransactions()
    }
}


