package com.neonusa.tanciku.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.neonusa.tanciku.presentation.get_started.GetStartedScreen
import com.neonusa.tanciku.presentation.home.HomeScreen
import com.neonusa.tanciku.presentation.navigator.MainNavigator

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.MainNavigation.route,
            startDestination = Route.MainNavigatorScreen.route
        ) {
            composable(route = Route.MainNavigatorScreen.route) {
                MainNavigator()
                //todo : implementasikan appentry untuk menyimpan state getstarted
                //todo : bikin viewmodel untuk getstartedscreen
                //todo : kemungknan besar butuh event juga
            }
        }
    }
}