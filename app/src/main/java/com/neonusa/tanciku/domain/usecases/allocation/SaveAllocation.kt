package com.neonusa.tanciku.domain.usecases.allocation

import com.neonusa.tanciku.domain.manager.LocalUserManager
import com.neonusa.tanciku.domain.model.Allocation

class SaveAllocation(private val localUserManger: LocalUserManager) {
    suspend operator fun invoke(allocation: Allocation){
        localUserManger.saveAllocation(allocation)
    }
}