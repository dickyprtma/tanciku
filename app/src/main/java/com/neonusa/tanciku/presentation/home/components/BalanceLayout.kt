package com.neonusa.tanciku.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.ui.theme.TancikuTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BalanceLayout(
    balance: String
) {
    val currentMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale("id"))
    } else {
        val calendar = Calendar.getInstance()
        calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id")) ?: "Bulan"
    }
    val currentYear = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().year
    } else {
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.YEAR)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp ,start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .align(Alignment.Bottom), // Memberikan ruang yang fleksibel untuk teks di kiri

        ) {
            Text(
                text = stringResource(id = R.string.balance),
                color = colorResource(id = R.color.text_title_large).copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )
            // Saldo
            Text(
                text = if (balance.startsWith("-")) {
                    "-Rp${balance.removePrefix("-")}"
                } else {
                    "Rp$balance"
                },
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleLarge
            )
        }
        Column(
            horizontalAlignment = Alignment.End, // Membuat elemen-alemen di kolom ini rata kanan
        ) {

            // Saldo
            Text(
                text = stringResource(id = R.string.period),
                color = colorResource(id = R.color.text_title_large).copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "$currentMonth $currentYear",
                color = colorResource(id = R.color.text_title_small),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBalanceLayout() {
    TancikuTheme {
        BalanceLayout("0")
    }
}