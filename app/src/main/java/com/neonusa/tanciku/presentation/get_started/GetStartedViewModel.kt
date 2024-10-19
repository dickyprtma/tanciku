package com.neonusa.tanciku.presentation.get_started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    fun onEvent(event: GetStartedEvent){
        when(event){
            is GetStartedEvent.SaveAppEntry ->{
                saveUserEntry()
            }
        }
    }

    private fun saveUserEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

}