package com.neonusa.tanciku.domain.usecases.transaction

data class TransactionUseCases(
    val insertTransaction: InsertTransaction,
    val deleteTransaction: DeleteTransaction,
    val deleteTransactionById: DeleteTransactionById,

    val getTotalIncomeForCurrentMonth: GetTotalIncomeForCurrentMonth,
    val getTotalExpenseForCurrentMonth: GetTotalExpenseForCurrentMonth,
)