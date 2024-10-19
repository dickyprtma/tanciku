package com.neonusa.tanciku.presentation.main_activity

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
    private val _startDestination = mutableStateOf(Route.GetStartedNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            if(shouldStartFromHomeScreen){
                _startDestination.value = Route.MainNavigation.route
            }else{
                _startDestination.value = Route.GetStartedNavigation.route
            }
            delay(200) // need this delay so the getStartedScreen not showing when we already fill the budget allocation
        }.launchIn(viewModelScope)
    }
}