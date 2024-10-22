package com.neonusa.tanciku.presentation.budget

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
class BudgetViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val allocationUseCases: AllocationUseCases
):ViewModel() {
    private val _currentMonthTotalExpense = MutableStateFlow(0)
    val currentMonthTotalExpense: StateFlow<Int> = _currentMonthTotalExpense

    private val _currentMonthTotalIncome = MutableStateFlow(0)
    val currentMonthTotalIncome: StateFlow<Int> = _currentMonthTotalIncome

    private val _currentMonthTotalNeeds = MutableStateFlow(0)
    val currentMonthTotalNeeds: StateFlow<Int> = _currentMonthTotalNeeds

    private val _currentMonthTotalWants = MutableStateFlow(0)
    val currentMonthTotalWants: StateFlow<Int> = _currentMonthTotalWants

    private val _currentMonthTotalSaving = MutableStateFlow(0)
    val currentMonthTotalSaving: StateFlow<Int> = _currentMonthTotalSaving

    private val _allocation = MutableStateFlow(Allocation(needs = 0, wants = 0, saving = 0))
    val allocation: StateFlow<Allocation> = _allocation

    /*
        Blok init berarti fungsi tersebut hanya dijalankan sekali saat ViewModel pertama kali diinisialisasi.
        Jika data diubah setelah ViewModel sudah diinisialisasi (misalnya, setelah penambahan transaksi di AddTransactionScreen),
        maka BudgetScreen tidak akan memperbarui state-nya secara otomatis.

        oleh karena itu perlu menambahkan Launched di MainNavigator agar data terupdate saat ada perubahan di screen lain
    */

    init {
        getCurrentMonthTotalIncome()
        getCurrentMonthTotalNeeds()
        getCurrentMonthTotalWants()
        getCurrentMonthTotalSaving()
        getCurrentMonthTotalExpense()
        getAllocation()
    }

    fun getCurrentMonthTotalExpense() {
        viewModelScope.launch {
            _currentMonthTotalExpense.value = transactionUseCases.getCurrentMonthTotalExpense()
        }
    }

    fun getCurrentMonthTotalIncome() {
        viewModelScope.launch {
            _currentMonthTotalIncome.value = transactionUseCases.getCurrentMonthTotalIncome()
        }
    }

    fun getCurrentMonthTotalNeeds() {
        viewModelScope.launch {
            _currentMonthTotalNeeds.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(
                TransactionCategory.Kebutuhan)
        }
    }

    fun getCurrentMonthTotalWants() {
        viewModelScope.launch {
            _currentMonthTotalWants.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(
                TransactionCategory.Keinginan)
        }
    }

    fun getCurrentMonthTotalSaving() {
        viewModelScope.launch {
            _currentMonthTotalSaving.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(
                TransactionCategory.Menabung)
        }
    }

    fun getAllocation() {
        viewModelScope.launch {
            allocationUseCases.readAllocation().collect { allocation ->
                _allocation.value = allocation
            }
        }
    }
}