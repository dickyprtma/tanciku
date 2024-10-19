package com.neonusa.tanciku.domain.usecases.allocation

import com.neonusa.tanciku.domain.usecases.app_entry.ReadAppEntry

data class AllocationUseCases (
    val saveAllocation: SaveAllocation,
    val getAllocation: GetAllocation
)