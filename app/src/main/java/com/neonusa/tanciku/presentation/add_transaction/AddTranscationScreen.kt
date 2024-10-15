package com.neonusa.tanciku.presentation.add_transaction

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neonusa.tanciku.presentation.add_transaction.components.AddTransactionToolbarLayout
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionTypeLayout

@Composable
fun AddTransactionScreen(
    navigateUp: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // rencananya nanti pilih tanggalnya kalo udh tekan centang aja
        AddTransactionToolbarLayout(onBackClick = navigateUp, onShareClick = {})
        TransactionTypeLayout()

//        DatePickerLayout()

    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddTranscationScreenPreview(){
    AddTransactionScreen(navigateUp = {})
}