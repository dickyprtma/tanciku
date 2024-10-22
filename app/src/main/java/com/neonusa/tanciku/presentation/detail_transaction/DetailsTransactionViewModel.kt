package com.neonusa.tanciku.presentation.detail_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsTransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
): ViewModel() {

    fun onEvent(event: DetailsTransactionEvent){
        when(event){
            is DetailsTransactionEvent.DeleteTransactionById -> {
                viewModelScope.launch {
                    deleteTransactionById(event.id)
                }
            }
        }
    }

    private suspend fun deleteTransactionById(id: Int){
        transactionUseCases.deleteTransactionById(id)
    }
}