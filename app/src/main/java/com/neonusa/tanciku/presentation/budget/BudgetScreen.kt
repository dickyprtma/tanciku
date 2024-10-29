package com.neonusa.tanciku.presentation.budget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BudgetScreen(
    totalExpense: Int,
    totalIncome: Int,
    totalNeeds: Int,
    totalWants: Int,
    totalSaving: Int,
    expensePercentage: Int,
    allocation: Allocation,
    navigateToEdit: () -> Unit
){
    // Debounce state
    var lastClickTime by remember { mutableLongStateOf(0L) }
    val debounceDelay = 1000L  // Delay in milliseconds

    // Budget Balance
    val allocatedNeedsAmount = (allocation.needs.toFloat()/100) * totalIncome
    val allocatedWantsAmount = (allocation.wants.toFloat()/100) * totalIncome
    val allocatedSavingAmount = (allocation.saving.toFloat()/100) * totalIncome


    val needsBalance = allocatedNeedsAmount - totalNeeds
    val formattedNeedsBalance = NumberFormat.getNumberInstance(Locale("id", "ID")).format(needsBalance.toInt())

    val wantsBalance = allocatedWantsAmount - totalWants
    val formattedWantsBalance = NumberFormat.getNumberInstance(Locale("id", "ID")).format(wantsBalance.toInt())

    val savingBalance = allocatedSavingAmount - totalSaving
    val formattedSavingBalance = NumberFormat.getNumberInstance(Locale("id", "ID")).format(savingBalance.toInt())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp,)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Anggaran",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.blue40), // Warna latar untuk badge
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable(
                        indication = null, // Menghilangkan efek ripple
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastClickTime >= debounceDelay) {
                            lastClickTime = currentTime
                            navigateToEdit()
                        }
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24), // Ganti dengan ID ikon edit yang sesuai
                    contentDescription = "Edit Icon",
                    tint = colorResource(id = R.color.white), // Sesuaikan warna ikon jika perlu
                    modifier = Modifier.size(16.dp) // Sesuaikan ukuran ikon jika perlu
                )
                Spacer(modifier = Modifier.width(4.dp)) // Jarak antara ikon dan teks
                Text(
                    text = "Ubah",
                    color = colorResource(id = R.color.white), // Warna emas atau warna yang menunjukkan premium
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        val needsUsedPercentage = if (totalIncome != 0) (totalNeeds.toFloat() / totalIncome.toFloat()) * 100 else 0f
        val wantsUsedPercentage = if (totalIncome != 0) (totalWants.toFloat() / totalIncome.toFloat()) * 100 else 0f
        val savingUsedPercentage = if (totalIncome != 0) (totalSaving.toFloat() / totalIncome.toFloat()) * 100 else 0f

        TotalExpense(expensePercentage = expensePercentage, totalExpense = totalExpense, totalIncome = totalIncome)

        HorizontalDivider(
            color = Color.Gray, // Warna garis
            thickness = 1.dp,   // Ketebalan garis
            modifier = Modifier.padding(start = 24.dp, end=24.dp, top = 16.dp) // Padding untuk memberi jarak
        )

        BudgetItem(transactionCategory = TransactionCategory.Kebutuhan, usedPercentage = needsUsedPercentage, allocation.needs.toFloat(), usedAmount = totalNeeds, totalIncome = totalIncome)
        BudgetItem(transactionCategory = TransactionCategory.Keinginan, usedPercentage = wantsUsedPercentage, allocation.wants.toFloat(), usedAmount = totalWants, totalIncome = totalIncome)
        BudgetItem(transactionCategory = TransactionCategory.Menabung, usedPercentage = savingUsedPercentage, allocation.saving.toFloat(), usedAmount = totalSaving, totalIncome = totalIncome)

        HorizontalDivider(
            color = Color.Gray, // Warna garis
            thickness = 1.dp,   // Ketebalan garis
            modifier = Modifier.padding(start = 24.dp, end=24.dp, top = 16.dp) // Padding untuk memberi jarak
        )

        Text(
            text = "Sisa Anggaran",
            fontSize = 19.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top=8.dp)
        )

        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Kebutuhan",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_title_small).copy(alpha = 0.7f)
            )
            Text(text = if (formattedNeedsBalance.startsWith("-")) {
                "-Rp${formattedNeedsBalance.removePrefix("-")}"
            } else {
                "Rp$formattedNeedsBalance"
            },
                style = MaterialTheme.typography.bodySmall)
        }

        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
                .fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Keinginan",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_title_small).copy(alpha = 0.7f)
            )
            Text(text = if (formattedWantsBalance.startsWith("-")) {
                "-Rp${formattedWantsBalance.removePrefix("-")}"
            } else {
                "Rp$formattedWantsBalance"
            },
                style = MaterialTheme.typography.bodySmall)
        }

        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Menabung",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_title_small).copy(alpha = 0.7f)
                )
            Text(text = if (formattedSavingBalance.startsWith("-")) {
                "+Rp${formattedSavingBalance.removePrefix("-")}"
            } else {
                "Rp$formattedSavingBalance"
            },
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BudgetScreenPreview(){
    BudgetScreen(totalIncome = 5000000, totalNeeds = 1500000, totalWants = 600000, totalSaving = 900000, allocation = Allocation(50,30,20), navigateToEdit = {}, totalExpense = 500400, expensePercentage = 50)
}



//Column(
//modifier = Modifier
//.fillMaxSize()
//.statusBarsPadding()
//.padding(16.dp)
//)