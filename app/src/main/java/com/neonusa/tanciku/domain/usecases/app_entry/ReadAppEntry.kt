package com.neonusa.tanciku.domain.usecases.app_entry

import com.neonusa.tanciku.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(private val localUserManger: LocalUserManager) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManger.readAppEntry()
    }

}