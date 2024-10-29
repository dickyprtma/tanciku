package com.neonusa.tanciku.presentation.main_activity

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.tanciku.domain.usecases.app_entry.AppEntryUseCases
import com.neonusa.tanciku.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    // initial value berpengaruh sama kecepatan eksekusi, jika ini tidak empty dia akan lebih berat (misalnya menggunakan Route.NamaScreen.route)
    private val _startDestination = mutableStateOf("")
    val startDestination: State<String> = _startDestination


    init {
        appEntryUseCases.readAppEntry().onEach { getStartedDone ->
            if(getStartedDone){
                _startDestination.value = Route.MainNavigation.route
            }else{
                _startDestination.value = Route.GetStartedScreen.route
            }
            delay(1000)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}