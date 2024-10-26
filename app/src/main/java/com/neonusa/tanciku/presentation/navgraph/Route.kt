package com.neonusa.tanciku.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
) {
    object GetStartedScreen : Route(route = "GetStartedScreen")

    object MainNavigation : Route(route = "MainNavigation")
    object MainNavigatorScreen : Route(route = "MainNavigatorScreen")
    object TransactionScreen : Route(route = "TransactionScreen")
    object HomeScreen : Route(route = "HomeScreen")
    object BudgetScreen : Route(route = "BudgetScreen")
    object EditBudgetScreen: Route(route = "EditBudgetScreen")
    object AddTransactionScreen: Route(route = "AddTransactionScreen")
    object EditTransactionScreen: Route(route = "EditTransactionScreen")


}