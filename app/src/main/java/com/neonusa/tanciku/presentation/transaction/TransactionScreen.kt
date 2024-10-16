package com.neonusa.tanciku.presentation.transaction

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TransactionScreen(){
    Column(
    ) {
        Text(text = "This is transaction screen")
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TransactionScreenPreview(){
    TransactionScreen()
}