package com.neonusa.tanciku.domain.usecases.allocation

import com.neonusa.tanciku.domain.manager.LocalUserManager

class SaveAllocation(private val localUserManger: LocalUserManager) {
    suspend operator fun invoke(kebutuhan: Int, keinginan: Int, menabung:Int){
        localUserManger.saveAllocation(kebutuhan)
    }
}