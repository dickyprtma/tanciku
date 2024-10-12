package com.neonusa.tanciku.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun SaldoLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f) // Memberikan ruang yang fleksibel untuk teks di kiri
        ) {
            Text(
                text = "Saldo",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Oktober 2024",
                style = MaterialTheme.typography.titleSmall
            )
        }
        Text(
            text = "Rp1.800.280",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSaldoLayout() {
    TancikuTheme {
        SaldoLayout()
    }
}