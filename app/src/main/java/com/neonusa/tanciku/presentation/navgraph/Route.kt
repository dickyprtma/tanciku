package com.neonusa.tanciku.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    // LEVEL 1
    object TransactionScreen : Route(route = "TransactionScreen")
    object HomeScreen : Route(route = "HomeScreen")
    object SettingsScreen : Route(route = "SettingsScreen")

    // LEVEL 2
    object AddTransactionScreen: Route(route = "AddTransactionScreen")

    object MainNavigation : Route(route = "MainNavigation")

    object MainNavigatorScreen : Route(route = "MainNavigatorScreen")
}