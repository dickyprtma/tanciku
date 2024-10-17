package com.neonusa.tanciku.presentation.add_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.usecases.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
): ViewModel() {
    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: AddTransactionEvent){
        when(event){
            is AddTransactionEvent.InsertTransaction -> {
                viewModelScope.launch {
                    insertTransaction(event.transaction)
                }
            }
            is AddTransactionEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun insertTransaction(transaction: Transaction){
        transactionUseCases.insertTransaction(transaction = transaction)
        sideEffect = "Transaksi Berhasil Ditambahkan"
    }
}