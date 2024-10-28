package com.neonusa.tanciku.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import com.neonusa.tanciku.presentation.common.DetailsTransactionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases): ViewModel() {
    val transactions = transactionUseCases.getTransactionsPaged().cachedIn(viewModelScope)

    fun onEvent(event: DetailsTransactionEvent){
        when(event){
            is DetailsTransactionEvent.DeleteTransactionById -> {
                viewModelScope.launch {
                    deleteTransactionById(event.id)
                }
            }
            is  DetailsTransactionEvent.EditTransaction -> {
                viewModelScope.launch {

                }
            }
        }
    }

    private suspend fun deleteTransactionById(id: Int){
        transactionUseCases.deleteTransactionById(id)
    }
}