package com.neonusa.tanciku.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
 import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.ui.theme.TancikuTheme
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: (() -> Unit)? = null
) {
    val iconTintColor = when (transaction.category) {
        TransactionCategory.Menabung -> colorResource(id = R.color.color_income)
        TransactionCategory.Pemasukan -> colorResource(id = R.color.color_income)
        TransactionCategory.Kebutuhan -> colorResource(id = R.color.color_expense)
        TransactionCategory.Keinginan -> colorResource(id = R.color.color_wants)
    }

    val iconResId = when(transaction.type){
        TransactionType.Pemasukan -> R.drawable.arrow_circle_up
        TransactionType.Pengeluaran -> R.drawable.arrow_circle_down
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Icon di sebelah kiri
        Icon(

            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = iconTintColor
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = transaction.description,
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = convertDate(transaction.date),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        val symbol = when (transaction.type) {
            TransactionType.Pemasukan -> "+"
            else -> "−"
        }

        val formattedAmount = NumberFormat.getNumberInstance(Locale("id", "ID")).format(transaction.amount)

        // Harga di ujung kanan
        Text(
            text = symbol+"Rp"+formattedAmount,
            color = colorResource(id = R.color.text_title_large),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

fun convertDate(inputDate: String): String {
    // Define the input and output formats
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d", Locale.getDefault()) // Handling single digit month/day
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID")) // Indonesian locale
    // Parse the input date
    val date = LocalDate.parse(inputDate, inputFormatter)
    // Format to the desired output format
    return date.format(outputFormatter)
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TransactionItemPreview(){
    TancikuTheme {
        TransactionItem(
            transaction = Transaction(0,"Membeli ayam","2024-10-05", TransactionType.Pengeluaran,TransactionCategory.Kebutuhan,25000) ,
            onClick = {})
    }
}