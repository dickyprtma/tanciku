package com.neonusa.tanciku.presentation.get_started

import android.content.res.Configuration
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.presentation.home.HomeScreen
import com.neonusa.tanciku.presentation.home.components.BudgetCircularItem
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun GetStartedScreen() {
    // Create a screen with a column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Center the BudgetCircularItem with a larger size
        BudgetCircularItem(
            title = "Pemasukan",
            usedPercentage = 100f,
            allocatedPercentage = 100f,
            progressBarColorResId = R.color.color_income
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Create a Row with three text fields for "kebutuhan", "keinginan", and "menabung/investasi"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InputField(label = "Kebutuhan", icon = "%", modifier = Modifier.weight(1f))
            InputField(label = "Keinginan", icon = "%", modifier = Modifier.weight(1f))
            InputField(label = "Menabung/Investasi", icon = "%", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun InputField(label: String, icon: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text(label) },
        leadingIcon = { Text(icon) },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GetStartedScreenPreview(){
    TancikuTheme {
        GetStartedScreen()
    }
}