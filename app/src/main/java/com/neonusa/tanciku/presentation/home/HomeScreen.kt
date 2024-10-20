package com.neonusa.tanciku.presentation.home

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.presentation.common.ListTransactionItem
import com.neonusa.tanciku.presentation.home.components.BalanceLayout
import com.neonusa.tanciku.presentation.home.components.BudgetCircularItem
import com.neonusa.tanciku.presentation.home.components.TransactionTotalItem
import com.neonusa.tanciku.ui.theme.TancikuTheme
import java.text.NumberFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    totalIncome: Int,
    totalExpense: Int,
    balance: Int,
    totalNeeds: Int,
    totalWants: Int,
    totalSaving: Int,
    allocation: Allocation,
    state: HomeState
){
    val formattedIncome = NumberFormat.getNumberInstance(Locale("id", "ID")).format(totalIncome)
    val formattedExpense = NumberFormat.getNumberInstance(Locale("id", "ID")).format(totalExpense)
    val formattedBalance = NumberFormat.getNumberInstance(Locale("id", "ID")).format(balance)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        BalanceLayout(formattedBalance)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Distribute space evenly between items
            verticalAlignment = Alignment.CenterVertically
        ){
            TransactionTotalItem(
                R.drawable.arrow_circle_up,
                colorResource(id = R.color.color_income),
                stringResource(id = R.string.income),
                "Rp$formattedIncome"
            )
            TransactionTotalItem(
                R.drawable.arrow_circle_down,
                colorResource(id = R.color.color_expense),
                stringResource(id = R.string.expense),
                "Rp$formattedExpense"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Alokasi Anggaran",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Selengkapnya >",
                color = colorResource(id = R.color.text_title_small).copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start= 16.dp, end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Distribute space evenly between items
            verticalAlignment = Alignment.CenterVertically
        ){
            val needsUsedPercentage = if (totalIncome != 0) (totalNeeds.toFloat() / totalIncome.toFloat()) * 100 else 0f
            val wantsUsedPercentage = if (totalIncome != 0) (totalWants.toFloat() / totalIncome.toFloat()) * 100 else 0f
            val savingUsedPercentage = if (totalIncome != 0) (totalSaving.toFloat() / totalIncome.toFloat()) * 100 else 0f

            BudgetCircularItem(
                title = "Kebutuhan",
                usedPercentage = needsUsedPercentage,
                allocatedPercentage = allocation.needs.toFloat(),
                progressBarColorResId = R.color.color_expense
            )

            BudgetCircularItem(
                title = "Keinginan",
                usedPercentage = wantsUsedPercentage,
                allocatedPercentage = allocation.wants.toFloat(),
                progressBarColorResId = R.color.color_wants
            )

            BudgetCircularItem(
                title = "Menabung",
                usedPercentage = savingUsedPercentage,
                allocatedPercentage = allocation.saving.toFloat(),
                progressBarColorResId = R.color.color_income
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Transaksi",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Lihat Semua >",
                color = colorResource(id = R.color.text_title_small).copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
        ListTransactionItem(transactions = state.transactions,onClick = {})
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenReview(){
    TancikuTheme {
        HomeScreen(0,0, 0, allocation = Allocation(0,0,0), totalNeeds = 0, totalWants = 0, totalSaving = 0, state = HomeState())
    }
}