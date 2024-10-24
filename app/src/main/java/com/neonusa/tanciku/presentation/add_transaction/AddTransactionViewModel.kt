package com.neonusa.tanciku.presentation.add_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val allocationUseCases: AllocationUseCases
): ViewModel() {
    var sideEffect by mutableStateOf<String?>(null)
        private set

    private val _allocation = MutableStateFlow(Allocation(needs = 0, wants = 0, saving = 0))
    val allocation: StateFlow<Allocation> = _allocation

    init {
        getAllocation()
    }

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

    private fun getAllocation() {
        viewModelScope.launch {
            allocationUseCases.readAllocation().collect { allocation ->
                _allocation.value = allocation
            }
        }
    }
}