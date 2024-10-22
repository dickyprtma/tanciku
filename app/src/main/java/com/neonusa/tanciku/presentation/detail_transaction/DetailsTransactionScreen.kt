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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neonusa.tanciku.R

@Composable
fun DetailsTransactionScreen() {
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
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow), // ganti dengan ikon edit
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.text_title_small)
                    )
                }

                Text(
                    text = "Detail Transaksi",
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

        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            painter = painterResource(id = R.drawable.arrow_circle_down),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            tint = colorResource(id = R.color.color_expense),
        )

        Text(
            text = "Rp980.000",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.text_title_small)
        )

        Text(
            text = "Membeli saham dan reksadana",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, start = 24.dp, end = 24.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.text_title_small)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "24 Februari 2024",
                color = colorResource(id = R.color.text_title_small)
                )
            Text(
                text = "Pemasukan",
                color = colorResource(id = R.color.text_title_small)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTransactionScreenPreview(){
    DetailsTransactionScreen()
}