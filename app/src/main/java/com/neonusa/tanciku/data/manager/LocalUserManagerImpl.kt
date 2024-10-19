package com.neonusa.tanciku.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.neonusa.tanciku.domain.manager.LocalUserManager
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.utils.Constants
import com.neonusa.tanciku.utils.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context
): LocalUserManager {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }

    override suspend fun saveAllocation(allocation: Allocation) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.NEEDS] = allocation.needs
            settings[PreferenceKeys.WANTS] = allocation.wants
            settings[PreferenceKeys.SAVING] = allocation.saving
        }
    }

    override suspend fun readAllocation(): Flow<Allocation> {
        return context.dataStore.data
            .map { settings ->
                val needs = settings[PreferenceKeys.NEEDS] ?: 0
                val wants = settings[PreferenceKeys.WANTS] ?: 0
                val saving = settings[PreferenceKeys.SAVING] ?: 0
                Allocation(needs, wants, saving)
            }
    }
}

private val readOnlyProperty = preferencesDataStore(name = USER_SETTINGS)
val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)

    val NEEDS = intPreferencesKey(Constants.NEEDS)
    val WANTS = intPreferencesKey(Constants.WANTS)
    val SAVING = intPreferencesKey(Constants.SAVING)
}