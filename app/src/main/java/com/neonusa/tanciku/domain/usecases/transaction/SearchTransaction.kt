package com.neonusa.tanciku.domain.usecases.transaction

import androidx.paging.PagingData
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class SearchTransaction(private val repository: TransactionRepository) {
    operator fun invoke(query: String): Flow<PagingData<Transaction>> {
        return repository.searchTransactions(query)
    }
}