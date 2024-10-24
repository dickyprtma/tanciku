package com.neonusa.tanciku.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IncomeAllocationInputField(
    label: String,
    icon: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false // Add isError parameter with a default value
) {
    OutlinedTextField(
        value = value,
        onValueChange = { updatedValue ->
            // Clean the value by removing leading zeros
            val cleanedValue = updatedValue.trimStart('0')
                .filter { char -> char.isDigit() }

            // Limit the value to a maximum of 2 digits
            val finalValue = when {
                cleanedValue.isEmpty() -> "0"
                cleanedValue.length > 2 -> cleanedValue.take(2) // Only allow up to 2 characters
                else -> cleanedValue
            }

            onValueChange(finalValue)
        },
        label = {
            Text(label, style = MaterialTheme.typography.labelSmall)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        trailingIcon = {
            Text(icon)
        },
        isError = isError // Apply the isError parameter to show error state
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IncomeAllocationInputFieldPreview(){
    IncomeAllocationInputField(label = "Pemasukan", icon = "%", value = "0", onValueChange = {})
}


