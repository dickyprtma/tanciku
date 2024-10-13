package com.neonusa.tanciku.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object TransactionScreen : Route(route = "TransactionScreen")
    object HomeScreen : Route(route = "HomeScreen")
    object SettingsScreen : Route(route = "SettingsScreen")

    object NewsNavigation : Route(route = "NewsNavigation")

    // represent navigator screen
    object NavigatorScreen : Route(route = "NavigatorScreen")
}