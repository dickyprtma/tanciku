package com.neonusa.tanciku.presentation.get_started

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.presentation.add_transaction.AddTransactionEvent
import com.neonusa.tanciku.presentation.common.IncomeAllocationInputField
import com.neonusa.tanciku.presentation.common.SuccessDialog
import com.neonusa.tanciku.presentation.navgraph.Route
import com.neonusa.tanciku.ui.theme.TancikuTheme
import kotlinx.coroutines.delay


@Composable
fun GetStartedScreen(
    onEvent: (GetStartedEvent) -> Unit
) {
    var usedPercentage by remember { mutableStateOf(0) }
    var allocatedPercentage by remember { mutableStateOf(100) }

    val kebutuhanError = remember { mutableStateOf(false) }
    val keinginanError = remember { mutableStateOf(false) }
    val menabungError = remember { mutableStateOf(false) }

    var kebutuhan by remember { mutableStateOf("0") }
    var keinginan by remember { mutableStateOf("0") }
    var menabung by remember { mutableStateOf("0") }

    var showAllocationError by remember { mutableStateOf(false) }
    var allocationErrorMessage by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    fun updateUsedPercentage() {
        val total = kebutuhan.toInt() + keinginan.toInt() + menabung.toInt()
        usedPercentage = total
    }

    fun validateAndShowErrors() {
        val total = kebutuhan.toInt() + keinginan.toInt() + menabung.toInt()

        when {
            total > 100 -> {
                showAllocationError = true
                allocationErrorMessage = "Total alokasi tidak boleh lebih dari 100%"
            }
            total < 100 -> {
                showAllocationError = true
                allocationErrorMessage = "Total alokasi tidak boleh kurang dari 100%"
            }
            else -> {
                showAllocationError = false
                allocationErrorMessage = ""
            }
        }

        kebutuhanError.value = kebutuhan == "0"
        keinginanError.value = keinginan == "0"
        menabungError.value = menabung == "0"

        if (kebutuhanError.value) {
            showAllocationError = true
            allocationErrorMessage = "Alokasi kebutuhan tidak boleh 0%"
        } else if (keinginanError.value) {
            showAllocationError = true
            allocationErrorMessage = "Alokasi keinginan tidak boleh 0%"
        } else if (menabungError.value) {
            showAllocationError = true
            allocationErrorMessage = "Alokasi menabung tidak boleh 0%"
        }

        // If no errors, show success dialog
        if (!showAllocationError) {
            showSuccessDialog = true
        }
    }

    // Reset allocation function
    fun resetAllocation() {
        kebutuhan = "50"
        keinginan = "30"
        menabung = "20"
        updateUsedPercentage() // Update usedPercentage after resetting values
        showAllocationError = false // Clear any existing errors
        allocationErrorMessage = ""
    }

    // Screen UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
    ) {

        Text(
            text = "Ayo Mulai!",
            color = colorResource(id = R.color.text_title_large),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top=24.dp, start = 16.dp, end=16.dp, bottom = 4.dp)
        )

        Text(
            text = "Mulai langkah pertama anda dengan mengalokasikan pemasukan",
            color = colorResource(id = R.color.text_title_small),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 48.dp,start = 16.dp, end=16.dp,)
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center

        ) {
            CircularProgressIndicator(
                progress = 1f, // 100%
                modifier = Modifier.size(180.dp),
                color = colorResource(id = R.color.color_gray_circular),
                strokeWidth = 20.dp,
            )

            CircularProgressIndicator(
                progress = usedPercentage.toFloat() / allocatedPercentage.toFloat(),
                modifier = Modifier
                    .size(180.dp)
                    .padding(1.dp),
                color = colorResource(id = R.color.color_income),
                strokeWidth = 20.dp
            )

            Text(
                text = "${usedPercentage.toInt()}/${allocatedPercentage.toInt()}%",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IncomeAllocationInputField(
                label = "Kebutuhan",
                icon = "%",
                value = kebutuhan,
                onValueChange = {
                    kebutuhan = it
                    updateUsedPercentage()
                },
                modifier = Modifier.weight(1f),
                isError = kebutuhanError.value
            )
            IncomeAllocationInputField(
                label = "Keinginan",
                icon = "%",
                value = keinginan,
                onValueChange = {
                    keinginan = it
                    updateUsedPercentage()
                },
                modifier = Modifier.weight(1f),
                isError = keinginanError.value
            )
            IncomeAllocationInputField(
                label = "Menabung",
                icon = "%",
                value = menabung.toString(),
                onValueChange = {
                    menabung = it
                    updateUsedPercentage()
                },
                modifier = Modifier.weight(1f),
                isError = menabungError.value
            )
        }

        // Display allocation error message
        if (showAllocationError) {
            Text(
                text = allocationErrorMessage,
                color = colorResource(
                    id = R.color.color_expense
                ),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                validateAndShowErrors()
            },
            modifier = Modifier.fillMaxWidth().padding(start= 8.dp, end= 8.dp)
        ) {
            Text("Alokasikan")
        }


        Text(
            text = "Jika masih bingung, kamu bisa menggunakan alokasi default. Tenang, alokasi anggaran bisa diubah kembali " +
                    "nanti.",
            color = colorResource(
                id = R.color.text_title_small
            ).copy(alpha = 0.5f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 24.dp, start = 12.dp, end = 12.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Gunakan Alokasi Default",
            color = Color.Blue.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    resetAllocation() // Reset allocation when clicked
                }
        )
    }

    if (showSuccessDialog) {
        SuccessDialog(description = "Pemasukan kamu berhasil dialokasikan")

        // Menggunakan LaunchedEffect untuk menunggu 2 detik sebelum mengubah state
        LaunchedEffect(Unit) {
            delay(1000) // Delay selama 2 detik
            showSuccessDialog = false
            showLoadingDialog = true
        }
        // insert ke database
        val allocation = Allocation(
            needs = kebutuhan.toInt(),
            wants = keinginan.toInt(),
            saving = menabung.toInt(),
        )
        onEvent(GetStartedEvent.SaveAllocation(allocation))
        onEvent(GetStartedEvent.SaveAppEntry) // pindah ke halaman utama sebab readAppEntry berubah

        if(showLoadingDialog){
            // loading
            // DialogPropertis : Menonaktifkan penghapusan dialog ketika menekan tombol kembali atau di luar dialog
            AlertDialog(
                onDismissRequest = {},
                containerColor = Color.Transparent, // Menghilangkan background dialog
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
                text = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp)
                        )
                    }
                },
                confirmButton = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GetStartedScreenPreview(){
    TancikuTheme {
        GetStartedScreen({})
    }
}