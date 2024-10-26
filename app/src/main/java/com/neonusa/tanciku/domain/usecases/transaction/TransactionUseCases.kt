package com.neonusa.tanciku.domain.usecases.transaction

data class TransactionUseCases(
    val insertTransaction: InsertTransaction,
    val deleteTransaction: DeleteTransaction,
    val updateTransaction: UpdateTransaction,
    val deleteTransactionById: DeleteTransactionById,

    val getTransactionsPaged: GetTransactionsPaged,
    val getCurrentMonthTotalIncome: GetCurrentMonthTotalIncome,
    val getCurrentMonthTotalExpense: GetCurrentMonthTotalExpense,
    val getCurrentMonthTotalTransactionByCategory: GetCurrentMonthTotalTransactionByCategory,

    val getCurrentMonthLatestTransactions: GetCurrentMonthLatestTransactions
    )