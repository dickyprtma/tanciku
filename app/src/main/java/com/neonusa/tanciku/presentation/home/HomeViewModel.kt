package com.neonusa.tanciku.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val allocationUseCases: AllocationUseCases
): ViewModel() {
    private val _currentMonthTotalIncome = MutableStateFlow(0)
    val currentMonthTotalIncome: StateFlow<Int> = _currentMonthTotalIncome

    private val _currentMonthTotalExpense = MutableStateFlow(0)
    val currentMonthTotalExpense: StateFlow<Int> = _currentMonthTotalExpense

    private val _currentMonthTotalNeeds = MutableStateFlow(0)
    val currentMonthTotalNeeds: StateFlow<Int> = _currentMonthTotalNeeds

    private val _currentMonthTotalWants = MutableStateFlow(0)
    val currentMonthTotalWants: StateFlow<Int> = _currentMonthTotalWants

    private val _currentMonthTotalSaving = MutableStateFlow(0)
    val currentMonthTotalSaving: StateFlow<Int> = _currentMonthTotalSaving

    private val _allocation = MutableStateFlow(Allocation(needs = 0, wants = 0, saving = 0))
    val allocation: StateFlow<Allocation> = _allocation


    init {
        getCurrentMonthTotalIncome()
        getCurrentMonthTotalExpense()
        getAllocation()

        getCurrentMonthTotalNeeds()
    }

    private fun getCurrentMonthTotalIncome() {
        viewModelScope.launch {
            _currentMonthTotalIncome.value = transactionUseCases.getCurrentMonthTotalIncome()
        }
    }

    private fun getCurrentMonthTotalExpense() {
        viewModelScope.launch {
            _currentMonthTotalExpense.value = transactionUseCases.getCurrentMonthTotalExpense()
        }
    }

    private fun getCurrentMonthTotalNeeds() {
        viewModelScope.launch {
            _currentMonthTotalNeeds.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(TransactionCategory.Kebutuhan)
        }
    }

    private fun getAllocation() {
        viewModelScope.launch {
            allocationUseCases.readAllocation().collect { allocation ->
                _allocation.value = allocation
            }
        }
    }
}