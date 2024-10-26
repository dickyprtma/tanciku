package com.neonusa.tanciku.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import com.neonusa.tanciku.presentation.common.DetailsTransactionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val allocationUseCases: AllocationUseCases
): ViewModel() {

    // catatan penting :
    // arsitektur viewmodel punya metode enkapsulasi yang membagi variabel menjadi
    // _namaVariabel : bisa diubah di dalam viewModel
    // namaVariabel : tidak bisa diubah (untuk diakses di luar viewmodel seperti view)

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

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

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

    init {
        getCurrentMonthTotalIncome()
        getCurrentMonthTotalExpense()
        getAllocation()

        getCurrentMonthTotalNeeds()
        getCurrentMonthTotalWants()
        getCurrentMonthTotalSaving()

        getCurrentMonthLatestTransactions()
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

    private fun getCurrentMonthTotalWants() {
        viewModelScope.launch {
            _currentMonthTotalWants.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(TransactionCategory.Keinginan)
        }
    }

    private fun getCurrentMonthTotalSaving() {
        viewModelScope.launch {
            _currentMonthTotalSaving.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(TransactionCategory.Menabung)
        }
    }

    private fun getAllocation() {
        viewModelScope.launch {
            allocationUseCases.readAllocation().collect { allocation ->
                _allocation.value = allocation
            }
        }
    }

    private fun getCurrentMonthLatestTransactions(){
        transactionUseCases.getCurrentMonthLatestTransactions().onEach {
            _state.value = _state.value.copy(transactions = it)
        }.launchIn(viewModelScope)
    }

    private suspend fun deleteTransactionById(id: Int){
        transactionUseCases.deleteTransactionById(id)

        // Perbarui StateFlow
        _currentMonthTotalIncome.value = transactionUseCases.getCurrentMonthTotalIncome()
        _currentMonthTotalExpense.value = transactionUseCases.getCurrentMonthTotalExpense()

        _currentMonthTotalNeeds.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(TransactionCategory.Kebutuhan)
        _currentMonthTotalWants.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(TransactionCategory.Keinginan)
        _currentMonthTotalSaving.value = transactionUseCases.getCurrentMonthTotalTransactionByCategory(TransactionCategory.Menabung)
    }


}