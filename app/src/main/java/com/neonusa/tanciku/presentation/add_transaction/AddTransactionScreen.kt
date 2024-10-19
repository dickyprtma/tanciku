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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.presentation.add_transaction.components.AddTransactionToolbarLayout
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionCategorySelection
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionTypeSelection
import java.text.NumberFormat
import java.util.Calendar

@Composable
fun AddTransactionScreen(
    navigateUp: () -> Unit,
    event: (AddTransactionEvent) -> Unit,
){
    var rawAmount by remember { mutableStateOf("0") } // for data input
    var amount by remember{ mutableStateOf("0")} // for data view
    var desc by remember{ mutableStateOf("")}

    var transactionType by remember { mutableStateOf("Pengeluaran")}
    var transactionCategory by remember { mutableStateOf("Kebutuhan")}

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Error states
    var amountError by remember { mutableStateOf<String?>(null) }
    var descError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // rencananya nanti pilih tanggalnya kalo udh tekan centang aja
        AddTransactionToolbarLayout(
            onBackClick = navigateUp,
            onCheckClick = {
                // Reset errors
                amountError = null
                descError = null

                // Validation logic
                val isValid = when {
                    rawAmount.isEmpty() || rawAmount.toIntOrNull() == null || rawAmount.toInt() <= 0 -> {
                        amountError = "Nominal harus lebih dari 0"
                        false
                    }
                    desc.isEmpty() -> {
                        descError = "Keterangan tidak boleh kosong"
                        false
                    }
                    else -> true
                }

                if (isValid) {
                    showDatePicker = true
                }
            })

        TransactionTypeSelection(
            selectedType = transactionType,
            onTypeChange = {newType ->
                transactionType = newType
                // Set category to "Kebutuhan" if type is "Pengeluaran"
                if (newType == "Pengeluaran") {
                    transactionCategory = "Kebutuhan"
                } else {
                    transactionCategory = "Pemasukan"
                }
                Log.d("Test@AddTransactionScreen", "AddTransactionScreen: $transactionType")
                Log.d("Test@AddTransactionScreen", "AddTransactionScreen: $transactionCategory")
            })

        OutlinedTextField(
            value = amount,
            leadingIcon = { Text("Rp") },
            onValueChange = { newText ->
                // Strip non-numeric characters for internal storage, except for commas or periods
                val unformatted = newText.replace(".", "").replace(",", "")

                // Update rawAmount for validation
                rawAmount = if (unformatted.isEmpty()) "0" else unformatted

                // Format the amount with thousands separators for display
                amount = NumberFormat.getInstance().format(unformatted.toLongOrNull() ?: 0)
            },
            label = { Text("Nominal") },
            isError = amountError != null,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp)
        )

        // Display the error message for the amount field
        if (amountError != null) {
            Text(
                text = amountError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }

        OutlinedTextField(
            value = desc,
            onValueChange = { newText ->
                desc = newText
            },
            label = { Text(text = "Keterangan") },
            isError = descError != null,
            shape = RoundedCornerShape(16.dp),
            maxLines = 5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .defaultMinSize(minHeight = 100.dp)
        )

        if (descError != null) {
            Text(
                text = descError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }

        if(transactionType == "Pengeluaran"){
            TransactionCategorySelection(
                selectedCategory = transactionCategory,
                onCategoryChange = {category ->
                    transactionCategory = category
                }
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
                    //todo : pastikan insert ke database hanya terjadi jika user menekan ok, jika menekan batal ini tidak tejadi
                    showDatePicker = false // Hide the DatePicker if dismissed

                    // convert transaction type and category string to enum
                    val transactionTypeEnum = when (transactionType) {
                        "Pengeluaran" -> TransactionType.Pengeluaran
                        "Pemasukan" -> TransactionType.Pemasukan
                        else -> TransactionType.Pengeluaran // Default value (jika ada)
                    }

                    val transactionCategoryEnum = when (transactionCategory) {
                        "Kebutuhan" -> TransactionCategory.Kebutuhan
                        "Keinginan" -> TransactionCategory.Keinginan
                        "Menabung" -> TransactionCategory.Menabung
                        else -> TransactionCategory.Pemasukan // Default value (jika ada)
                    }

                    // insert ke database
                    val transaction = Transaction(
                        type = transactionTypeEnum,
                        amount = rawAmount.toInt(),
                        description = desc,
                        category = transactionCategoryEnum,
                        date = selectedDate
                    )
                    event(AddTransactionEvent.InsertTransaction(transaction))
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
            val selectedDate = "$selectedYear-${selectedMonth+1}-$selectedDay"
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
    AddTransactionScreen(navigateUp = {}, event = {})
}