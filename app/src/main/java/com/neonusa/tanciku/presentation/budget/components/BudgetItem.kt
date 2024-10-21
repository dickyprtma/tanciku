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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.TransactionCategory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BudgetItem(
    transactionCategory: TransactionCategory,
    usedPercentage: Float,
    allocatedPercentage: Float,
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
                text = "$formattedUsedAmount / Dummy",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

//            val textColorId = if(transactionCategory != TransactionCategory.Menabung){
//                // jika kategori kebutuhan/keinginan
//                if(usedAmount >= allocatedAmount){
//                    R.color.color_expense
//                } else if (usedAmount > (allocatedAmount / 2) && usedAmount < allocatedAmount) {
//                    R.color.color_wants
//                }
//                else {
//                    R.color.color_income
//                }
//            } else {
//                if(usedAmount >= allocatedAmount){
//                    R.color.color_income
//                } else {
//                    R.color.text_transaction_type_add_transaction_wants_bg_selected
//                }
//            }
//
//            val textStatus = if(transactionCategory != TransactionCategory.Menabung){
//                // jika kategori kebutuhan/keinginan
//                if(usedAmount >= allocatedAmount){
//                    "Buruk"
//                } else if (usedAmount > (allocatedAmount / 2) && usedAmount < allocatedAmount) {
//                    "Sedang"
//                }
//                else {
//                    "Bagus!"
//                }
//            } else {
//                if(usedAmount >= allocatedAmount){
//                    "Bagus!"
//                } else {
//                    "Belum tercapai"
//                }
//            }

            Text(
                text = "Status : Dummy",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_title_small),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
     
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BudgetItemPreview(){
    BudgetItem(transactionCategory = TransactionCategory.Kebutuhan,  usedPercentage = 20f ,allocatedPercentage = 30f, 2000000)
}