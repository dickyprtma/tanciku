package com.neonusa.tanciku.domain.usecases.allocation

import com.neonusa.tanciku.domain.manager.LocalUserManager
import com.neonusa.tanciku.domain.model.Allocation
import kotlinx.coroutines.flow.Flow

class ReadAllocation(private val localUserManger: LocalUserManager) {
    operator fun invoke(): Flow<Allocation> {
        return localUserManger.readAllocation()
    }
}