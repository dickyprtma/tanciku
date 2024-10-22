package com.neonusa.tanciku.presentation.budget

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.presentation.budget.components.BudgetCircularItemLarge
import com.neonusa.tanciku.presentation.budget.components.BudgetItem
import com.neonusa.tanciku.presentation.budget.components.TotalExpense
import com.neonusa.tanciku.presentation.home.components.BudgetCircularItem
import com.neonusa.tanciku.presentation.home.components.convertDate

@Composable
fun BudgetScreen(
    totalIncome: Int,
    totalNeeds: Int,
    totalWants: Int,
    totalSaving: Int,
    allocation: Allocation,
    navigateToEdit: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Anggaran",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(onClick = {navigateToEdit()}) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_edit_24), // ganti dengan ikon edit
                    contentDescription = "Edit"
                )
            }
        }

        val needsUsedPercentage = if (totalIncome != 0) (totalNeeds.toFloat() / totalIncome.toFloat()) * 100 else 0f
        val wantsUsedPercentage = if (totalIncome != 0) (totalWants.toFloat() / totalIncome.toFloat()) * 100 else 0f
        val savingUsedPercentage = if (totalIncome != 0) (totalSaving.toFloat() / totalIncome.toFloat()) * 100 else 0f

        //todo : ambil data real
        TotalExpense(usedPercentage = 20, allocatedpercentage = 50)

        Spacer(modifier = Modifier.height(12.dp))

        BudgetItem(transactionCategory = TransactionCategory.Kebutuhan, usedPercentage = needsUsedPercentage, allocation.needs.toFloat(), usedAmount = totalNeeds, totalIncome = totalIncome)
        BudgetItem(transactionCategory = TransactionCategory.Keinginan, usedPercentage = wantsUsedPercentage, allocation.wants.toFloat(), usedAmount = totalWants, totalIncome = totalIncome)
        BudgetItem(transactionCategory = TransactionCategory.Menabung, usedPercentage = savingUsedPercentage, allocation.saving.toFloat(), usedAmount = totalSaving, totalIncome = totalIncome)

        // todo : isi layar kosong ini dengan sedikit informasi terkait penganggaran (seperti kebutuhan : blablablabal, dst.)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BudgetScreenPreview(){
    BudgetScreen(totalIncome = 5000000, totalNeeds = 1500000, totalWants = 600000, totalSaving = 900000, allocation = Allocation(50,30,20), navigateToEdit = {})
}



//Column(
//modifier = Modifier
//.fillMaxSize()
//.statusBarsPadding()
//.padding(16.dp)
//)