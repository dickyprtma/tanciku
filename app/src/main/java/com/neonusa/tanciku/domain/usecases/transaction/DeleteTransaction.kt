package com.neonusa.tanciku.domain.usecases.transaction

import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.repository.TransactionRepository

class DeleteTransaction(private val repository: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction){
        repository.deleteTransaction(transaction)
    }
}