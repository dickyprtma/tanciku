package com.neonusa.tanciku.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.usecases.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
): ViewModel() {
    private val _totalIncome = MutableStateFlow(0)
    val totalIncome: StateFlow<Int> = _totalIncome

    private val _totalExpense = MutableStateFlow(0)
    val totalExpense: StateFlow<Int> = _totalExpense

    init {
        getTotalIncome()
        getTotalExpense()
    }

    private fun getTotalIncome() {
        viewModelScope.launch {
            _totalIncome.value = transactionUseCases.getTotalIncome()
        }
    }

    private fun getTotalExpense() {
        viewModelScope.launch {
            _totalExpense.value = transactionUseCases.getTotalExpense()
        }
    }
}