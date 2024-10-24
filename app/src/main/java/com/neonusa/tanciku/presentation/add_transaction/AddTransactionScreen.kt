package com.neonusa.tanciku.presentation.add_transaction

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.presentation.add_transaction.components.AddTransactionToolbarLayout
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionCategorySelection
import com.neonusa.tanciku.presentation.add_transaction.components.TransactionTypeSelection
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

// next update 1.1.0
// kasih peringatan ke user terkait kebutuhan atau pengeluarannya jika udah berlebih
// masih dipertimbangkan untuk jadi fitur tanciku+ atau tidak

//todo : user experience waktu input nominal masih buruk cursornya
@Composable
fun AddTransactionScreen(
    navigateUp: () -> Unit,
    event: (AddTransactionEvent) -> Unit,
    allocation: Allocation,
    totalIncome: Int,
    totalSaving: Int,
    ){

    var rawAmount by remember { mutableStateOf("0") } // for data input
    var amount by remember{ mutableStateOf("0")} // for data view
    var desc by remember{ mutableStateOf("")}
    var selectedDate by remember { mutableStateOf("") }

    var transactionType by remember { mutableStateOf("Pengeluaran")}
    var transactionCategory by remember { mutableStateOf("Kebutuhan")}

    var showDatePicker by remember { mutableStateOf(false) }
    var showSavingRecommendation by remember { mutableStateOf(false) }
    var isSavingRecommendationAlreadyChoosed by remember { mutableStateOf(false) }


    val context = LocalContext.current

    // Error states
    var amountError by remember { mutableStateOf<String?>(null) }
    var descError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
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

        // rencananya nanti pilih tanggalnya kalo udh tekan centang aja
        AddTransactionToolbarLayout(
            onBackClick = navigateUp,
            onCheckClick = {
                // Reset errors
                amountError = null
                descError = null

                // Validation logic
                val isValid = when {
                    rawAmount.isEmpty() || rawAmount.toFloat().toInt() <= 0 -> {
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
            onTypeChange = { newType ->
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

        if (transactionType == "Pengeluaran") {
            TransactionCategorySelection(
                selectedCategory = transactionCategory,
                onCategoryChange = { category ->
                    transactionCategory = category
                    if (category == "Menabung") {
                        showSavingRecommendation = true
                    }
                }
            )
        }

        val allocatedAmount = (allocation.saving.toFloat() / 100) * totalIncome
        val amountLeft = allocatedAmount - totalSaving
        val formattedAmountLeft = NumberFormat.getNumberInstance(Locale("id", "ID")).format(amountLeft)

        if (transactionCategory == "Menabung") {
            if(amountLeft > 0){
                if(!isSavingRecommendationAlreadyChoosed){
                    if (showSavingRecommendation) {
                        Text(
                            text = "Masih kurang Rp$formattedAmountLeft untuk memenuhi target ${allocation.saving}% menabung. Apakah kamu ingin memenuhi target menabungmu?",
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_title_small).copy(alpha = 0.6f)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                        ) {
                            Button(
                                onClick = {
                                    amount =  formattedAmountLeft
                                    showSavingRecommendation = false
                                    rawAmount = amountLeft.toString()
                                    isSavingRecommendationAlreadyChoosed = true
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp)
                                    .border(
                                        1.dp,
                                        colorResource(id = R.color.color_income),
                                        shape = RoundedCornerShape(50)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = colorResource(id = R.color.color_income)
                                )
                            ) {
                                Text("Ya")
                            }
                            Button(
                                onClick = {
                                    showSavingRecommendation = false
                                    isSavingRecommendationAlreadyChoosed = true
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                                    .border(
                                        1.dp,
                                        colorResource(id = R.color.color_expense),
                                        shape = RoundedCornerShape(50)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = colorResource(id = R.color.color_expense)
                                ),
                            ) {
                                Text("Tidak")
                            }
                        }
                    }

                }

            }

        }

        if (showDatePicker) {
            ShowDatePicker(
                context = context,
                onDateSelected = { date ->
                    selectedDate = date
                    showDatePicker = false // Hide the DatePicker once a date is selected

                    // insert ke database jika sudah memilih tanggal dan menekan ok
                    val transaction = Transaction(
                        type = transactionTypeEnum,
                        amount = rawAmount.toFloat().toInt(),
                        description = desc,
                        category = transactionCategoryEnum,
                        date = selectedDate
                    )
                    event(AddTransactionEvent.InsertTransaction(transaction))
                    Log.d("AddTransactionScreen@Test", "dateselected and ok pressed")
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
            val formattedDay = String.format(Locale.getDefault(), "%02d", selectedDay)
            val selectedDate = "$selectedYear-${selectedMonth+1}-$formattedDay"
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
    AddTransactionScreen(navigateUp = {}, event = {}, allocation = Allocation(50,30,20), totalIncome = 5000000, totalSaving = 500000)
}