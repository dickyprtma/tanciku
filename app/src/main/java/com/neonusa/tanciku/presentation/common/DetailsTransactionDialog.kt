package com.neonusa.tanciku.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailsTransactionDialog(
    onDismiss: () -> Unit,
    transaction: Transaction,
    event: (DetailsTransactionEvent) -> Unit,
    navigateToEditTransaction: (Transaction) -> Unit
) {
    val formattedAmount = NumberFormat.getNumberInstance(Locale("id", "ID")).format(transaction.amount)

    // Debounce state
    var lastClickTime by remember { mutableLongStateOf(0L) }
    val debounceDelay = 1000L  // Delay in milliseconds
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    onDismiss()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_clear_24), // ganti dengan ikon edit
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.text_title_small)
                    )
                }

                Text(
                    text = "Transaksi",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.text_title_small)
                )
            }

            Row {
                IconButton(onClick = {event(
                    DetailsTransactionEvent.DeleteTransactionById(
                        transaction.id!!
                    )
                )}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.trash), // ganti dengan ikon edit
                        contentDescription = "Hapus",
                        Modifier.size(24.dp),
                        tint = colorResource(id = R.color.color_expense)
                    )
                }
                IconButton(onClick = {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime >= debounceDelay) {
                        lastClickTime = currentTime
                        navigateToEditTransaction(transaction)
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_edit_24), // ganti dengan ikon edit
                        contentDescription = "Edit",
                        Modifier.size(24.dp),
                        tint = colorResource(id = R.color.text_title_small)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val iconResId = when(transaction.type){
            TransactionType.Pemasukan -> R.drawable.arrow_circle_up
            TransactionType.Pengeluaran -> R.drawable.arrow_circle_down
        }

        val iconTintColor = when (transaction.category) {
            TransactionCategory.Menabung -> colorResource(id = R.color.color_income)
            TransactionCategory.Pemasukan -> colorResource(id = R.color.color_income)
            TransactionCategory.Kebutuhan -> colorResource(id = R.color.color_expense)
            TransactionCategory.Keinginan -> colorResource(id = R.color.color_wants)
        }

        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally),
            tint = iconTintColor,
        )

        Text(
            text = "Rp$formattedAmount",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp, start = 24.dp, end = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.text_title_small)
        )

        Text(
            text = transaction.description,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, start = 24.dp, end = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_title_small),
            fontSize = 13.sp
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = convertDate(transaction.date),
                color = colorResource(id = R.color.text_title_small),
                fontSize = 12.sp)
            Text(
                text = transaction.category.name,
                color = colorResource(id = R.color.text_title_small),
                fontSize = 12.sp
            )
        }
    }
}

fun convertDate(inputDate: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d", Locale.getDefault()) // Handling single digit month/day
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID")) // Indonesian locale
    val date = LocalDate.parse(inputDate, inputFormatter)
    return date.format(outputFormatter)
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTransactionDialogPreview(){
    DetailsTransactionDialog(
        onDismiss = {},
        transaction = Transaction(0,"Membeli ayam geprek","2024-10-10",TransactionType.Pengeluaran, TransactionCategory.Kebutuhan,10000),
        event = {},
        navigateToEditTransaction = {})
}