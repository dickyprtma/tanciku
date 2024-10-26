package com.neonusa.tanciku.presentation.edit_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val allocationUseCases: AllocationUseCases
): ViewModel() {
    var sideEffect by mutableStateOf<String?>(null)
        private set

    private val _currentMonthTotalSaving = MutableStateFlow(0)
    val currentMonthTotalSaving: StateFlow<Int> = _currentMonthTotalSaving

    private val _currentMonthTotalIncome = MutableStateFlow(0)
    val currentMonthTotalIncome: StateFlow<Int> = _currentMonthTotalIncome

    private val _allocation = MutableStateFlow(Allocation(needs = 0, wants = 0, saving = 0))
    val allocation: StateFlow<Allocation> = _allocation

    init {
        getAllocation()
        getCurrentMonthTotalSaving()
        getCurrentMonthTotalIncome()
    }

    fun onEvent(event: EditTransactionEvent){
        when(event){
            is EditTransactionEvent.EditTransaction -> {
                viewModelScope.launch {
//                    insertTransaction(event.transaction) todo
                }
            }
            is EditTransactionEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private fun getAllocation() {
        viewModelScope.launch {
            allocationUseCases.readAllocation().collect { allocation ->
                _allocation.value = allocation
            }
        }
    }

    fun getCurrentMonthTotalSaving() {
        viewModelScope.launch {
            _currentMonthTotalSaving.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(
                TransactionCategory.Menabung)
        }
    }

    fun getCurrentMonthTotalIncome() {
        viewModelScope.launch {
            _currentMonthTotalIncome.value = transactionUseCases.getCurrentMonthTotalIncome()
        }
    }
}