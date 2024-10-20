package com.neonusa.tanciku.presentation.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.presentation.home.components.TransactionItem

@Composable
fun ListTransactionitem(
    transactions: List<Transaction>,
    onClick:(Transaction) -> Unit) {
    LazyColumn {
        items(count = transactions.size) {
            val transaction = transactions[it]
            TransactionItem(
                transaction = transaction, onClick = {onClick(transaction)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListTransactionItem() {
    // Data dummy untuk preview
    val dummyItems = listOf(
        Transaction(
            description = "Transaction 1",
            type = TransactionType.Pemasukan,
            category = TransactionCategory.Pemasukan,
            date = "20 Oktober 2024",
            amount = 20000000
        )
    )

    // Panggil ListTransactionItem dengan data dummy
    ListTransactionitem(transactions = dummyItems, {})
}