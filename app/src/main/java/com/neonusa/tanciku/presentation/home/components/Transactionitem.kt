package com.neonusa.tanciku.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
 import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun TransactionItem(
    iconResId: Int,
    title: String,
    type: String,
    date: String,
    price: String
) {
    val iconTintColor = when (type) {
        "menabung" -> colorResource(id = R.color.color_income)
        "kebutuhan" -> colorResource(id = R.color.color_expense)
        "keinginan" -> colorResource(id = R.color.color_wants)
        else -> colorResource(id = R.color.text_title_large) // Default color if no type matches
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon di sebelah kiri
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = iconTintColor
        )

        // Kolom untuk nama item (title) dan tanggal (subitem)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = title,
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // Harga di ujung kanan
        Text(
            text = price,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TransactionItemPreview(){
    TancikuTheme {
        TransactionItem(iconResId = R.drawable.arrow_circle_down, title = "Beli geprek", date = "3 Oktober 2024", price = "Rp10.000.000", type = "kebutuhan")
    }
}