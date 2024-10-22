package com.neonusa.tanciku.presentation.edit_allocation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBudgetViewModel @Inject constructor(
    private val allocationUseCases: AllocationUseCases
) :ViewModel() {

    private val _allocation = MutableStateFlow(Allocation(needs = 0, wants = 0, saving = 0))
    val allocation: StateFlow<Allocation> = _allocation

    fun onEvent(event: EditAllocationEvent){
        when(event){
            is EditAllocationEvent.SaveAllocation ->{
                saveAllocation(event.allocation)
            }
            else -> {}
        }
    }

    init {
        getAllocation()
    }

    private fun getAllocation() {
        viewModelScope.launch {
            allocationUseCases.readAllocation().collect { allocation ->
                _allocation.value = allocation
            }
        }
    }

    private fun saveAllocation(allocation:Allocation) {
        viewModelScope.launch {
            allocationUseCases.saveAllocation(allocation)
        }
    }
}