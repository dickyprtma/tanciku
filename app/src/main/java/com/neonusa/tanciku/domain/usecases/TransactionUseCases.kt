package com.neonusa.tanciku.domain.usecases

import com.neonusa.tanciku.domain.usecases.transaction.DeleteTransaction
import com.neonusa.tanciku.domain.usecases.transaction.DeleteTransactionById
import com.neonusa.tanciku.domain.usecases.transaction.GetTotalIncome
import com.neonusa.tanciku.domain.usecases.transaction.InsertTransaction

data class TransactionUseCases(
    val insertTransaction: InsertTransaction,
    val deleteTransaction: DeleteTransaction,
    val deleteTransactionById: DeleteTransactionById,

    val getTotalIncome: GetTotalIncome,
)