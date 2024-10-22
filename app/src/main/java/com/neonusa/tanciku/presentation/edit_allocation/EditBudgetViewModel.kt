package com.neonusa.tanciku.presentation.edit_allocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
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
}