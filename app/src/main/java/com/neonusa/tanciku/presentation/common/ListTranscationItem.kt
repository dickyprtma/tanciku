package com.neonusa.tanciku.presentation.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.neonusa.tanciku.presentation.home.components.TransactionItem

@Composable
fun ListTransactionitem(itemList: List<ItemData>) {
    LazyColumn {
        items(itemList) { item ->
            TransactionItem(
                iconResId = item.iconResId,  // Pastikan nama field sama dengan model data
                title = item.title,
                type = item.type,
                date = item.date,
                price = item.price
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListTransactionItem() {
    // Data dummy untuk preview
    val dummyItems = listOf(
        ItemData(
            iconResId = android.R.drawable.ic_menu_camera, // Ikon bawaan Android sebagai contoh
            title = "Transaction 1",
            type = "Kebutuhan",
            date = "12 Oct 2024",
            price = "Rp900.000"
        ),
        ItemData(
            iconResId = android.R.drawable.ic_menu_gallery, // Ikon bawaan Android sebagai contoh
            title = "Transaction 2",
            type = "Kebutuhan",
            date = "13 Oct 2024",
            price = "Rp1.200.000"
        ),
        ItemData(
            iconResId = android.R.drawable.ic_menu_compass, // Ikon bawaan Android sebagai contoh
            title = "Transaction 3",
            type = "Kebutuhan",
            date = "14 Oct 2024",
            price = "Rp750.000"
        )
    )

    // Panggil ListTransactionItem dengan data dummy
    ListTransactionitem(itemList = dummyItems)
}

data class ItemData(
    val iconResId: Int,
    val title: String,
    val type: String,
    val date: String,
    val price: String
)