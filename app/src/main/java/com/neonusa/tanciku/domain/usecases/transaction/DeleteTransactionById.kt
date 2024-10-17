package com.neonusa.tanciku.domain.usecases.transaction

import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.repository.TransactionRepository

class DeleteTransactionById(private val repository: TransactionRepository) {
    suspend operator fun invoke(id: Int){
        repository.deleteTransactionById(id)
    }
}