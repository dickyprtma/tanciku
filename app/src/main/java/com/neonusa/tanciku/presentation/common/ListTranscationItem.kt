package com.neonusa.tanciku.presentation.common

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.presentation.home.components.TransactionItem

@Composable
fun ListTransactionItem(
    transactions: List<Transaction>,
    onClick:(Transaction) -> Unit,
    ) {
    if (transactions.isEmpty()){
        var startAnimation by remember {
            mutableStateOf(false)
        }

        val alphaAnimation by animateFloatAsState(
            targetValue = if (startAnimation) 0.3f else 0f,
            animationSpec = tween(durationMillis = 1000)
        )

        LaunchedEffect(key1 = true) {
            startAnimation = true
        }

        EmptyContent(alphaAnim = alphaAnimation, message = "Belum ada transaksi", iconId = R.drawable.no_transaction)
    } else {
        LazyColumn {
            items(count = transactions.size) {
                val transaction = transactions[it]
                TransactionItem(
                    transaction = transaction, onClick = {onClick(transaction)}
                )
            }
        }
    }
}

@Composable
fun ListTransactionItem(
    transactions: LazyPagingItems<Transaction>,
    onClick:(Transaction) -> Unit,
    emptyMessage: String
) {
    val handlePagingResult = handlePagingResult(transactions,emptyMessage)
    if (handlePagingResult) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = transactions.itemCount,
            ) {
                transactions[it]?.let { transaction ->
                    TransactionItem(transaction = transaction, onClick = { onClick(transaction) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(articles: LazyPagingItems<Transaction>,emptyMessage: String): Boolean {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            Log.d("Test", "handlePagingResult: Loading...")
            false
        }

        error != null || articles.itemCount == 0 -> {
            EmptyContent(alphaAnim = alphaAnimation, message = emptyMessage, iconId = R.drawable.no_transaction)
            false
        }
        else -> {
            Log.d("Test", "handlePagingResult: Success...")
            true
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
    ListTransactionItem(transactions = dummyItems, {})
}