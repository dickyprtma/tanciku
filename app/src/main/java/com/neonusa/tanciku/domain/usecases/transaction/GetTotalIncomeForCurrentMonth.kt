package com.neonusa.tanciku.domain.usecases.transaction

import com.neonusa.tanciku.domain.repository.TransactionRepository

class GetTotalIncomeForCurrentMonth(private val repository: TransactionRepository) {
    suspend operator fun invoke(): Int {
        return repository.getTotalIncomeForCurrentMonth()
    }
}