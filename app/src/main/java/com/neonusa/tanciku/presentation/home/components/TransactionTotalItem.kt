package com.neonusa.tanciku.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun TransactionTotalItem(
    iconResId: Int, color: Color, title: String, amount: String,
){
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(16.dp)) // Jarak antara Icon dan teks

        // Column untuk teks title dan amount di sebelah kanan icon
        Column {
            Text(
                text = title,
                color = colorResource(id = R.color.text_title_large),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = amount,
                color = colorResource(id = R.color.text_title_small),
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TransactionTotalItemPreview(){
    TancikuTheme {
        TransactionTotalItem(
            R.drawable.arrow_circle_up,
            colorResource(id = R.color.color_income),
            stringResource(id = R.string.income),
            stringResource(id = R.string.expense))
    }
}