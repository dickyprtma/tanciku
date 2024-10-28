package com.neonusa.tanciku.presentation.search

import androidx.paging.PagingData
import com.neonusa.tanciku.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val transactions: Flow<PagingData<Transaction>>? = null
)