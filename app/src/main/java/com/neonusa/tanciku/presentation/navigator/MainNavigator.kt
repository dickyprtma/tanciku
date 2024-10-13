package com.neonusa.tanciku.presentation.navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neonusa.tanciku.R
import com.neonusa.tanciku.presentation.home.HomeScreen
import com.neonusa.tanciku.presentation.navgraph.Route
import com.neonusa.tanciku.presentation.navigator.components.BottomNavigationItem
import com.neonusa.tanciku.presentation.navigator.components.MainBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.receipt, text = "Transaksi"),
            BottomNavigationItem(icon = R.drawable.home, text = "Beranda"),
            BottomNavigationItem(icon = R.drawable.chart_pie_alt, text = "Anggaran"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.TransactionScreen.route -> 0
        Route.HomeScreen.route -> 1
        Route.SettingsScreen.route -> 2
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.TransactionScreen.route ||
                backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SettingsScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            MainBottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.TransactionScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.SettingsScreen.route
                        )
                    }
                },
                onFabClick = {}
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()


        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.TransactionScreen.route) {

            }
            composable(route = Route.HomeScreen.route) { backStackEntry ->
                HomeScreen(
                )
            }
            composable(route = Route.SettingsScreen.route) {
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}