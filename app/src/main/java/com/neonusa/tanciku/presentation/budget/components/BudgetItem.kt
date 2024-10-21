package com.neonusa.tanciku.presentation.budget.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.TransactionCategory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BudgetItem(
    transactionCategory: TransactionCategory,
    usedPercentage: Float,
    allocatedPercentage: Float,
    totalIncome: Int,
    usedAmount: Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val formattedUsedAmount = NumberFormat.getNumberInstance(Locale("id", "ID")).format(usedAmount)

        val allocatedAmount = (allocatedPercentage/100) * totalIncome
        val formattedAllocatedAmount = NumberFormat.getNumberInstance(Locale("id", "ID")).format(allocatedAmount.toInt())

        BudgetCircularItemLarge(transactionCategory,usedPercentage=usedPercentage, allocatedPercentage=allocatedPercentage)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = transactionCategory.name,
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Rp$formattedUsedAmount / $formattedAllocatedAmount",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            val status = if(transactionCategory == TransactionCategory.Kebutuhan || transactionCategory == TransactionCategory.Keinginan){
                when {
                    usedAmount == allocatedAmount.toInt() -> "Pas!"
                    usedAmount > allocatedAmount -> "Buruk"
                    usedAmount >= (allocatedAmount / 2) && usedAmount <= (allocatedAmount - 1) -> "Sedang"
                    usedAmount < (allocatedAmount / 2) -> "Baik!"
                    else -> "Tidak diketahui" // fallback if needed
                }
            } else {
                when {
                    usedAmount == allocatedAmount.toInt() -> "Pas!"
                    usedAmount > allocatedAmount -> "Baik!"
                    else -> "Belum tercapai"
                }
            }

            var textColorId = when {
                status == "Pas!" -> R.color.color_income
                status == "Buruk" -> R.color.color_expense
                status == "Sedang" -> R.color.text_transaction_type_add_transaction_wants_bg_selected
                status == "Baik!" -> R.color.color_income
                else -> R.color.text_title_small

            }

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.text_title_small))) {
                        append("Status: ") // Bagian "Status :" dengan warna hitam
                    }
                    withStyle(style = SpanStyle(color = colorResource(id = textColorId))) {
                        append(status) // Bagian "$status" dengan warna mengikuti textColorId
                    }
                },
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = textColorId),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
     
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BudgetItemPreview(){
    BudgetItem(transactionCategory = TransactionCategory.Kebutuhan,  usedPercentage = 3f ,allocatedPercentage = 50f, totalIncome = 3128000, usedAmount = 100000)
}