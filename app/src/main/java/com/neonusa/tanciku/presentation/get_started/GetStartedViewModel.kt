package com.neonusa.tanciku.presentation.get_started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.usecases.allocation.AllocationUseCases
import com.neonusa.tanciku.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val AllocationUseCases: AllocationUseCases,
) : ViewModel() {
    fun onEvent(event: GetStartedEvent){
        when(event){
            is GetStartedEvent.SaveAppEntry ->{
                saveUserEntry()
            }
            is GetStartedEvent.SaveAllocation ->{
                saveAllocation(event.allocation)
            }
        }
    }

    private fun saveUserEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

    private fun saveAllocation(allocation:Allocation) {
        viewModelScope.launch {
            AllocationUseCases.saveAllocation(allocation)
        }
    }

}