package com.neonusa.tanciku.presentation.home.components

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun BalanceLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f) // Memberikan ruang yang fleksibel untuk teks di kiri
        ) {
            Text(
                text = stringResource(id = R.string.balance),
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Oktober 2024",
                color = colorResource(id = R.color.text_title_small),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            text = "Rp4.200.000",
            color = colorResource(id = R.color.text_title_large),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBalanceLayout() {
    TancikuTheme {
        BalanceLayout()
    }
}