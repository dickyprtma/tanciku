package com.neonusa.tanciku.presentation.add_transaction

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.presentation.add_transaction.components.AddTransactionToolbarLayout
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionCategorySelection
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionTypeSelection
import java.util.Calendar

@Composable
fun AddTransactionScreen(
    navigateUp: () -> Unit
){
    var nominal by remember{ mutableStateOf("0")}
    var keterangan by remember{ mutableStateOf("")}

    var transactionType by remember { mutableStateOf("Pengeluaran")}
    var transactionCategory by remember { mutableStateOf("Kebutuhan")}

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // rencananya nanti pilih tanggalnya kalo udh tekan centang aja
        AddTransactionToolbarLayout(
            onBackClick = navigateUp,
            onCheckClick = {
                showDatePicker = true
            })

        TransactionTypeSelection(
            selectedCategory = transactionType,
            onCategoryChange = {newType ->
                transactionType = newType
                Log.d("Test@AddTransactionScreen", "AddTransactionScreen: $transactionType")
            })

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
        if(transactionType == "Pengeluaran"){
            TransactionCategorySelection()
        }

        // Display the selected date if available
        if (selectedDate.isNotEmpty()) {
            Text(
                text = "Selected Date: $selectedDate",
                modifier = Modifier.padding(16.dp)
            )
        }

        if (showDatePicker) {
            ShowDatePicker(
                context = context,
                onDateSelected = { date ->
                    selectedDate = date
                    showDatePicker = false // Hide the DatePicker once a date is selected
                },
                onDismissRequest = {
                    showDatePicker = false // Hide the DatePicker if dismissed
                }
            )
        }

    }
}

@Composable
fun ShowDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(selectedDate)
        },
        year,
        month,
        day
    ).apply {
        setOnDismissListener { onDismissRequest() }
        show()
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddTransactionScreenPreview(){
    AddTransactionScreen(navigateUp = {})
}