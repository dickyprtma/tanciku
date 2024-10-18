package com.neonusa.tanciku.domain.usecases.app_entry

import com.neonusa.tanciku.domain.manager.LocalUserManager

class SaveAppEntry(private val localUserManger: LocalUserManager) {
    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }

}