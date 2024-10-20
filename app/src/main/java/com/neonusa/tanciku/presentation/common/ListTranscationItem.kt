package com.neonusa.tanciku.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.presentation.home.components.TransactionItem

@Composable
fun ListTransactionItem(
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

@Composable
fun EmptyContent(alphaAnim: Float, message: String, iconId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnim)
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
        )
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
    ListTransactionItem(transactions = dummyItems, {})
}