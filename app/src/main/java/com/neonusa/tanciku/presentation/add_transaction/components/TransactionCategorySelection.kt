package com.neonusa.tanciku.presentation.add_transaction.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R

@Composable
fun TransactionCategorySelection() {
    var selectedCategory by remember { mutableStateOf("Kebutuhan") }
    Row(
        modifier = Modifier.padding()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TransactionCategoryItem(
            label = "Kebutuhan",
            isSelected = selectedCategory == "Kebutuhan",
            onClick = { selectedCategory = "Kebutuhan" },
            borderColor = if (selectedCategory == "Kebutuhan") Color(0xFFE53935) else Color(0xFF757575),
            backgroundColor = Color(0xFFFFEBEE),
        )

        TransactionCategoryItem(
            isSelected = selectedCategory == "Keinginan",
            label = "Keinginan",
            onClick = { selectedCategory = "Keinginan" },
            borderColor = if (selectedCategory == "Keinginan") Color(0xFFD6C96F) else Color(0xFF757575),
            backgroundColor = Color(0xFFE8F5E9),
        )

        TransactionCategoryItem(
            isSelected = selectedCategory == "Menabung",
            label = "Menabung",
            onClick = { selectedCategory = "Menabung" },
            borderColor = if (selectedCategory == "Menabung") Color(0xFF43A047) else Color(0xFF757575),
            backgroundColor = Color(0xFFE8F5E9),
        )
    }
}

@Composable
fun TransactionCategoryItem(
    isSelected: Boolean,
    label: String,
    onClick: () -> Unit,
    borderColor: Color,
    backgroundColor: Color,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = if (isSelected) backgroundColor else Color.White,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp)
    ) {
        val textColor = if(label == "Kebutuhan"){
            R.color.text_transaction_type_add_transaction_expense
        } else if(label == "Keinginan"){
            R.color.text_transaction_type_add_transaction_wants
        } else if(label == "Pemasukan" || label == "Menabung"){
            R.color.text_transaction_type_add_transaction_income
        } else {
            R.color.text_transaction_type_add_transaction_wants
        }
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp),
            text = label,
            color = colorResource(id = textColor),
            style = MaterialTheme.typography.titleSmall)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TransactionCategorySelectionPreview() {
    TransactionCategorySelection()
}