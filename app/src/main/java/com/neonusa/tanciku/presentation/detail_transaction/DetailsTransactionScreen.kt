package com.neonusa.tanciku.presentation.detail_transaction

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun DetailsTransactionScreen(
    onDismiss: () -> Unit,
    transaction: Transaction
) {
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow), // ganti dengan ikon edit
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
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.trash), // ganti dengan ikon edit
                        contentDescription = "Hapus",
                        Modifier.size(24.dp),
                        tint = colorResource(id = R.color.color_expense)
                    )
                }
                IconButton(onClick = {}) {
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

        Icon(
            painter = painterResource(id = R.drawable.arrow_circle_down),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally),
            tint = colorResource(id = R.color.color_expense),
        )

        Text(
            text = "Rp${transaction.amount}",
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
                text = "24 Februari 2024",
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

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTransactionScreenPreview(){
    DetailsTransactionScreen(
        onDismiss = {},
        transaction = Transaction(0,"Membeli ayam geprek","2024-10-10",TransactionType.Pengeluaran, TransactionCategory.Kebutuhan,10000))
}