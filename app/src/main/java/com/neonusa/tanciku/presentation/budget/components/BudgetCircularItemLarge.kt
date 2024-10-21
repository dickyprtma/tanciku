package com.neonusa.tanciku.presentation.budget.components

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.sp
import com.neonusa.tanciku.R

@Composable
fun BudgetCircularItemLarge() {
    Box(modifier = Modifier
        .size(70.dp),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            progress = {
                50f // 100% to show the full allocation
            },
            modifier = Modifier.size(70.dp),
            color = colorResource(id = R.color.color_gray_circular), // Gray color for unused portion
            strokeWidth = 8.dp,
        )

        Text(
            text = "20/50%",
            color = colorResource(id = R.color.text_title_large),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable()
fun BudgetCircularItemLargePreview(){
    BudgetCircularItemLarge()
}
