package com.neonusa.tanciku.presentation.edit_allocation

import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionEvent
import com.neonusa.tanciku.presentation.get_started.GetStartedEvent

sealed class EditAllocationEvent {
    data class SaveAllocation(val allocation: Allocation): EditAllocationEvent()
    object RemoveSideEffect: EditAllocationEvent()

}