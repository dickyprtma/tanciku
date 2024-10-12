package com.neonusa.tanciku.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R

@Composable
fun BudgetCircularItem(
    title: String,
    usedPercentage: Float,
    allocatedPercentage: Float,
    progressBarColorResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            // Circular progress indicator
            CircularProgressIndicator(
                progress = { usedPercentage / allocatedPercentage },
                modifier = Modifier.size(100.dp),
                color = colorResource(id = progressBarColorResId),
                strokeWidth = 8.dp,
            )

            // Display the percentage inside the circle
            Text(
                text = "${usedPercentage.toInt()}/${allocatedPercentage.toInt()}%",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Add the "Pengeluaran" label below the progress bar
        Text(
            text = title,
            color = colorResource(id = R.color.text_title_large),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 8.dp) // Add some space above the text
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Prev(){
    BudgetCircularItem(
        "Pengeluaran",
        usedPercentage = 20f,
        allocatedPercentage = 30f,
        progressBarColorResId = R.color.color_income)
}