package com.neonusa.tanciku.presentation.add_transaction

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.presentation.add_transaction.components.AddTransactionToolbarLayout
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionCategorySelection
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionTypeSelection

@Composable
fun AddTransactionScreen(
    navigateUp: () -> Unit
){
    var nominal by remember{ mutableStateOf("0")}
    var keterangan by remember{ mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // rencananya nanti pilih tanggalnya kalo udh tekan centang aja
        AddTransactionToolbarLayout(onBackClick = navigateUp, onShareClick = {})
        TransactionTypeSelection()
        OutlinedTextField(
            value = nominal,
            leadingIcon = {
                Text("Rp")
            },
            onValueChange = { newText ->
                nominal = newText
            },
            label = {Text("Nominal")},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp))

        OutlinedTextField(
            value = keterangan,
            onValueChange = { newText ->
                keterangan = newText
            },
            label = { Text(text = "Keterangan")},
            shape = RoundedCornerShape(16.dp),
            maxLines =  5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .defaultMinSize(minHeight = 100.dp)
        )
        TransactionCategorySelection()
//        DatePickerLayout()

    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddTranscationScreenPreview(){
    AddTransactionScreen(navigateUp = {})
}