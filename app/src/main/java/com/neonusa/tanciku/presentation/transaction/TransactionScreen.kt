package com.neonusa.tanciku.presentation.transaction

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.presentation.Dimens.MediumPadding1
import com.neonusa.tanciku.presentation.common.SearchBar

@Composable
fun TransactionScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transaksi",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )

         }

        SearchBar(text = "Cari transaksi...", readOnly = true, onValueChange = {}) {
            
        }
        //todo tampilkan data transaction
//        ListTransactionitem(itemList = dummyItems)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TransactionScreenPreview(){
    TransactionScreen()
}