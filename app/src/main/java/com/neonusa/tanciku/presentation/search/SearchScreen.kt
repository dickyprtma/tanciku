package com.neonusa.tanciku.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.presentation.common.ListTransactionItem
import com.neonusa.tanciku.presentation.common.SearchBar

@Composable
fun SearchScreen(
    state: SearchState,
    event:(SearchEvent) -> Unit,
    onTransactionItemClicked: (Transaction) -> Unit,
    navigateUp: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navigateUp() }
                    .padding(end = 8.dp)
            )
            Text(
                text = "Cari Transaksi",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )

        }

        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )

        state.transactions?.let { items ->
            val transactions = items.collectAsLazyPagingItems()
            ListTransactionItem(
                transactions = transactions,
                onClick = {
                    onTransactionItemClicked(it)
                },
                emptyMessage = "Transaksi tidak ditemukan"

            )
        }
    }
}
