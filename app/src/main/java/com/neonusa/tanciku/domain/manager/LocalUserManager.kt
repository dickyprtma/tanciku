package com.neonusa.tanciku.domain.manager

import com.neonusa.tanciku.domain.model.Allocation
import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>

    suspend fun saveAllocation(allocation: Allocation)
    fun readAllocation(): Flow<Allocation>
}