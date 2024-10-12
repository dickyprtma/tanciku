package com.neonusa.tanciku.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.presentation.home.components.BalanceLayout
import com.neonusa.tanciku.presentation.home.components.BudgetCircularItem
import com.neonusa.tanciku.presentation.home.components.TransactionItem
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        BalanceLayout()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Distribute space evenly between items
            verticalAlignment = Alignment.CenterVertically
        ){
            TransactionItem(
                R.drawable.arrow_circle_up,
                colorResource(id = R.color.color_income),
                stringResource(id = R.string.income),
                "Rp3.000.000"
            )
            TransactionItem(
                R.drawable.arrow_circle_down,
                colorResource(id = R.color.color_expense),
                stringResource(id = R.string.expense),
                "Rp128.500"
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Distribute space evenly between items
            verticalAlignment = Alignment.CenterVertically
        ){
            BudgetCircularItem(
                title = "Kebutuhan",
                usedPercentage = 20f,
                allocatedPercentage = 50f,
                progressBarColorResId = R.color.color_expense
            )

            BudgetCircularItem(
                title = "Keinginan",
                usedPercentage = 23f,
                allocatedPercentage = 20f,
                progressBarColorResId = R.color.color_wants
            )

            BudgetCircularItem(
                title = "Menabung",
                usedPercentage = 31f,
                allocatedPercentage = 30f,
                progressBarColorResId = R.color.color_income
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenReview(){
    TancikuTheme {
        HomeScreen()
    }
}