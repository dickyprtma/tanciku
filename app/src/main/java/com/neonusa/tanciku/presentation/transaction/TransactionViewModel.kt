package com.neonusa.tanciku.presentation.transaction

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val transactionUseCases: TransactionUseCases): ViewModel() {
    val transactions = transactionUseCases.getTransactionsPaged().cachedIn(viewModelScope)
}