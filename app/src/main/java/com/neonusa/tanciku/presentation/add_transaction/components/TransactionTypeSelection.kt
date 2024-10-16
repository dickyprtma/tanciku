package com.neonusa.tanciku.presentation.add_transaction.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R

@Composable
fun TransactionTypeSelection(
    selectedCategory: String,
    onCategoryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TransactionTypeItem(
            isSelected = selectedCategory == "Pengeluaran",
            label = "Pengeluaran",
            onClick = { onCategoryChange("Pengeluaran") },
            borderColor = if (selectedCategory == "Pengeluaran") Color(0xFFE53935) else Color(0xFF757575),
            backgroundColor = if (selectedCategory == "Pengeluaran") Color(0xFFE53935).copy(alpha = 0.1f) else colorResource(id = R.color.text_transaction_type_add_transaction_bg),
            iconColor = Color(0xFFE53935),
        )

        TransactionTypeItem(
            isSelected = selectedCategory == "Pemasukan",
            label = "Pemasukan",
            onClick = { onCategoryChange("Pemasukan") },
            borderColor = if (selectedCategory == "Pemasukan") Color(0xFF43A047) else Color(0xFF757575),
            backgroundColor = if (selectedCategory == "Pemasukan") Color(0xFF43A047).copy(alpha = 0.1f) else colorResource(id = R.color.text_transaction_type_add_transaction_bg),
            iconColor = Color(0xFF43A047)
        )
    }
}

@Composable
fun TransactionTypeItem(
    isSelected: Boolean,
    label: String,
    onClick: () -> Unit,
    borderColor: Color,
    backgroundColor: Color,
    iconColor: Color,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = if (label == "Pengeluaran") R.drawable.arrow_circle_down else R.drawable.arrow_circle_up),
                contentDescription = "$label Icon",
                tint = iconColor,
                modifier = Modifier.size(20.dp) // Atur ukuran ikon secara eksplisit
            )
            Spacer(modifier = Modifier.width(8.dp))

            val textColor = if (isSelected) {
                when (label) {
                    "Pengeluaran" -> R.color.text_transaction_type_add_transaction_expense
                    else -> R.color.text_transaction_type_add_transaction_income
                }
            } else {
                R.color.text_title_small
            }

            Text(
                text = label,
                color = colorResource(id = textColor),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    TransactionTypeSelection("Pengeluaran",{})
}