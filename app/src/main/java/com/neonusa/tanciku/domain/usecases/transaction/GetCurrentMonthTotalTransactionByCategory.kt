package com.neonusa.tanciku.domain.usecases.transaction

import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.repository.TransactionRepository

class GetCurrentMonthTotalTransactionByCategory(private val repository: TransactionRepository) {
    suspend operator fun invoke(category: TransactionCategory): Int{
        return repository.getCurentMonthTotalTransactionByCategory(category)
    }
}