package com.neonusa.tanciku.presentation.edit_allocation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Allocation
import com.neonusa.tanciku.presentation.common.IncomeAllocationInputField
import com.neonusa.tanciku.presentation.common.SuccessDialog
import com.neonusa.tanciku.presentation.get_started.GetStartedEvent
import com.neonusa.tanciku.ui.theme.TancikuTheme
import kotlinx.coroutines.delay

@Composable
fun EditBudgetScreen(
    onEvent: (EditAllocationEvent) -> Unit,
    navigateUp: () -> Unit,
    allocation: Allocation
) {
    val usedPercentageInit = allocation.needs + allocation.wants + allocation.saving
    var usedPercentage by remember { mutableStateOf(usedPercentageInit) }
    var allocatedPercentage by remember { mutableStateOf(100) }

    val kebutuhanError = remember { mutableStateOf(false) }
    val keinginanError = remember { mutableStateOf(false) }
    val menabungError = remember { mutableStateOf(false) }

    var kebutuhan by remember { mutableStateOf(allocation.needs.toString()) }
    var keinginan by remember { mutableStateOf(allocation.wants.toString()) }
    var menabung by remember { mutableStateOf(allocation.saving.toString()) }

    var showAllocationError by remember { mutableStateOf(false) }
    var allocationErrorMessage by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Ganti dengan ikon back yang kamu inginkan
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        onClick = { navigateUp() },
                        indication = null, // Menghilangkan efek bayangan saat diklik
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )

            Spacer(modifier = Modifier.weight(1f)) // Memberi jarak agar teks berada di tengah

            Text(
                text = "Ubah Alokasi",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(8f) // Menyebar teks di tengah
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f)) // Memberi jarak di kanan teks
        }

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
                text = "${usedPercentage}/${allocatedPercentage.toInt()}%",
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Text("Ubah Alokasi")
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
            color = colorResource(id = R.color.blue40),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(
                    onClick = { resetAllocation() },
                    indication = null, // Menghilangkan efek bayangan saat diklik
                    interactionSource = remember { MutableInteractionSource() }
                )


        )
    }

    if (showSuccessDialog) {
        SuccessDialog(description = "Pemasukan kamu berhasil dialokasikan")
        LaunchedEffect(Unit) {
            delay(3000) // Delay 3 detik

            // insert ke database
            val newAllocation = Allocation(
                needs = kebutuhan.toInt(),
                wants = keinginan.toInt(),
                saving = menabung.toInt(),
            )
            onEvent(EditAllocationEvent.SaveAllocation(newAllocation))
            showSuccessDialog = false
            navigateUp()
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EditBudgetScreenPreview(){
    TancikuTheme {
        EditBudgetScreen({}, {}, allocation = Allocation(0,0,0))
    }
}