package com.neonusa.tanciku.presentation.get_started

import com.neonusa.tanciku.domain.model.Allocation

sealed class GetStartedEvent {
    object SaveAppEntry: GetStartedEvent()
    data class SaveAllocation(val allocation: Allocation): GetStartedEvent()
}