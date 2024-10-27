package com.neonusa.tanciku.presentation.budget.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.sp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.TransactionCategory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TotalExpense(
    expensePercentage: Int,
    totalExpense: Int,
    totalIncome: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val formattedTotalExpense = NumberFormat.getNumberInstance(Locale("id", "ID")).format(totalExpense)
        val formattedTotalIncome = NumberFormat.getNumberInstance(Locale("id", "ID")).format(totalIncome)

        Box(modifier = Modifier
            .size(80.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                progress = {
                    50f // 100% to show the full allocation
                },
                modifier = Modifier.size(80.dp),
                color = colorResource(id = R.color.color_gray_circular), // Gray color for unused portion
                strokeWidth = 16.dp,
            )

            // Inner circle (represents used percentage)
            CircularProgressIndicator(
                progress = expensePercentage.toFloat() / 100,
                modifier = Modifier
                    .size(100.dp)
                    .padding(1.dp), // To prevent overlap
                color = colorResource(id = R.color.blue80), // Color for used portion
                strokeWidth = 16.dp
            )

            Text(
                text = "$expensePercentage%",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = "Total Pengeluaran",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Rp$formattedTotalExpense / $formattedTotalIncome",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TotalExpensePreview() {
    TotalExpense(20,1000000,3000000)
}