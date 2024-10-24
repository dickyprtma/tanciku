package com.neonusa.tanciku.presentation.navigator

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionEvent
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionScreen
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionViewModel
import com.neonusa.tanciku.presentation.budget.BudgetScreen
import com.neonusa.tanciku.presentation.budget.BudgetViewModel
import com.neonusa.tanciku.presentation.common.AdMobBannerAd
import com.neonusa.tanciku.presentation.common.DetailsTransactionDialog
import com.neonusa.tanciku.presentation.edit_allocation.EditBudgetScreen
import com.neonusa.tanciku.presentation.edit_allocation.EditBudgetViewModel
import com.neonusa.tanciku.presentation.home.HomeScreen
import com.neonusa.tanciku.presentation.home.HomeViewModel
import com.neonusa.tanciku.presentation.navgraph.Route
import com.neonusa.tanciku.presentation.navigator.components.BottomNavigationItem
import com.neonusa.tanciku.presentation.navigator.components.MainBottomNavigation
import com.neonusa.tanciku.presentation.transaction.TransactionScreen

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
        Route.BudgetScreen.route -> 2
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.TransactionScreen.route ||
                backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.BudgetScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        Column {
            if (!isBottomBarVisible) {
                AdMobBannerAd(modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
            } else {
                AdMobBannerAd()
            }
            
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
                                route = Route.BudgetScreen.route
                            )
                        }
                    },
                    onFabClick = {
                        navigateToAddTransaction(navController = navController)
                        Log.d("FAB", "Plus button clicked!")
                    }
                )
            }
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.TransactionScreen.route) {
                TransactionScreen()
            }
            composable(route = Route.HomeScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val totalIncome by viewModel.currentMonthTotalIncome.collectAsState()
                val totalExpense by viewModel.currentMonthTotalExpense.collectAsState()

                val totalNeeds by viewModel.currentMonthTotalNeeds.collectAsState()
                val totalWants by viewModel.currentMonthTotalWants.collectAsState()
                val totalSaving by viewModel.currentMonthTotalSaving.collectAsState()

                // derivedStateOf : state turunan yang nilainya bergantung dengan state yang lain
                val balance by derivedStateOf { totalIncome - totalExpense }

                val allocation: Allocation by viewModel.allocation.collectAsState()

                val state = viewModel.state.value

                var showDialog by remember { mutableStateOf(false) }
                var transaction by remember { mutableStateOf<Transaction?>(null)}

                // details view model
//                val detailsTransactionViewModel: DetailsTransactionViewModel = hiltViewModel()

                if (showDialog) {
                    Dialog(onDismissRequest = { showDialog = false }) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surface,
                            contentColor = contentColorFor(MaterialTheme.colorScheme.surface)
                        ) {
                            DetailsTransactionDialog(
                                transaction = transaction!!,
                                onDismiss = {showDialog = false},
                                event = { detailsTransactionEvent ->
                                    viewModel.onEvent(detailsTransactionEvent) // Memanggil onEvent dengan parameter yang dikirim
                                    showDialog = false
                                }
                            )
                        }
                    }
                }

                HomeScreen(
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    balance = balance,
                    allocation = allocation,
                    totalNeeds = totalNeeds,
                    totalWants = totalWants,
                    totalSaving = totalSaving,
                    state = state,
                    navigateToBudget = {
                        navigateToBudget(navController)
                    },
                    navigateToTransaction = {
                        navigateToTransaction(navController)
                    },
                    onTransactionItemClicked = {
                        Log.d("TEST", "MainNavigator: $it")
                        transaction = it
                        showDialog = true
                    })
            }
            composable(route = Route.BudgetScreen.route) {
                val viewModel: BudgetViewModel = hiltViewModel()
                val totalIncome by viewModel.currentMonthTotalIncome.collectAsState()
                val totalExpense by viewModel.currentMonthTotalExpense.collectAsState()
                val totalNeeds by viewModel.currentMonthTotalNeeds.collectAsState()
                val totalWants by viewModel.currentMonthTotalWants.collectAsState()
                val totalSaving by viewModel.currentMonthTotalSaving.collectAsState()
                val allocation: Allocation by viewModel.allocation.collectAsState()

                val expensePercentage by derivedStateOf {
                    if (totalIncome != 0) {
                        (totalExpense.toFloat() / totalIncome.toFloat()) * 100
                    } else {
                        0
                    }
                }

                //LaunchedEffect digunakan untuk memicu ulang pemanggilan fungsi setiap kali layar BudgetScreen dimasuki
                LaunchedEffect(Unit) {
                    viewModel.getCurrentMonthTotalExpense()
                    viewModel.getCurrentMonthTotalIncome()
                    viewModel.getCurrentMonthTotalNeeds()
                    viewModel.getCurrentMonthTotalWants()
                    viewModel.getCurrentMonthTotalSaving()
                    viewModel.getAllocation()
                }

                BudgetScreen(
                    allocation = allocation,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    totalNeeds = totalNeeds,
                    totalWants = totalWants,
                    totalSaving = totalSaving,
                    expensePercentage = expensePercentage.toInt(),
                    navigateToEdit = { navigateToEditBudget(navController) })
            }

            composable(route = Route.AddTransactionScreen.route) {
                val viewModel: AddTransactionViewModel = hiltViewModel()

                var showDialog by remember { mutableStateOf(false) }
                if (viewModel.sideEffect != null && !showDialog) {
                    showDialog = true
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                        },
                        title = { Text("Success") },
                        text = { Text("Data transaksi berhasil Ditambahkan") },
                        confirmButton = {

                        }
                    )

                    // Automatically dismiss after 2 seconds
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        showDialog = false
                        viewModel.onEvent(AddTransactionEvent.RemoveSideEffect)
                        navController.navigate(Route.HomeScreen.route) {
                            popUpTo(Route.HomeScreen.route) { inclusive = true }
                        }
                    }
                }

                AddTransactionScreen(
                    navigateUp = {navController.navigateUp()},
                    event = viewModel::onEvent
                )
            }

            composable(route = Route.EditBudgetScreen.route){
                val viewModel: EditBudgetViewModel = hiltViewModel()
                val allocation: Allocation by viewModel.allocation.collectAsState()
                EditBudgetScreen(
                    onEvent = viewModel::onEvent,
                    navigateUp = {navController.navigateUp()},
                    allocation = allocation
                )
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

private fun navigateToAddTransaction(navController: NavController){
    navController.navigate(
        route = Route.AddTransactionScreen.route
    )
}

private fun navigateToBudget(navController: NavController){
    navController.navigate(route = Route.BudgetScreen.route) {
        popUpTo(Route.HomeScreen.route) { saveState = true } // pastikan kembali ke HomeScreen
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToEditBudget(navController: NavController){
    navController.navigate(
        route = Route.EditBudgetScreen.route
    )
}

private fun navigateToTransaction(navController: NavController){
    navController.navigate(route = Route.TransactionScreen.route) {
        popUpTo(Route.HomeScreen.route) { saveState = true } // pastikan kembali ke HomeScreen
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
private fun MainNavigatorPreview(){
    MainNavigator()
}