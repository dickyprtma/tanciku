package com.neonusa.tanciku.presentation.navigator.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neonusa.tanciku.R
import com.neonusa.tanciku.ui.theme.TancikuTheme

@Composable
fun MainBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit // Tambahkan event untuk FAB
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Bottom Navigation Bar
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 10.dp
        ) {
            items.forEachIndexed { index, item ->
                if (index != 1) { // Mengabaikan item di posisi tengah
                    NavigationBarItem(
                        selected = index == selectedItem,
                        onClick = { onItemClick(index) },
                        icon = {
                            Column(horizontalAlignment = CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = colorResource(id = R.color.body),
                            unselectedTextColor = colorResource(id = R.color.body),
                            indicatorColor = MaterialTheme.colorScheme.background
                        ),
                    )
                }
            }
        }

        // Floating Action Button (FAB)
        FloatingActionButton(
            onClick = {
                if (selectedItem == 1) {
                    Log.d("FAB", "Plus button clicked!")
                } else {
                    onItemClick(1)
                }
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(56.dp) // Ukuran FAB lebih besar
                .align(Alignment.TopCenter) // Posisi FAB di tengah atas
                .offset(y = (-28).dp) // Setengah dari ukuran FAB untuk mengangkatnya
        ) {
            // Menentukan ikon FAB berdasarkan selectedItem
            val fabIcon = if (selectedItem == 1) {
                R.drawable.plus // Ikon plus saat "Home" dipilih
            } else {
                R.drawable.home // Ikon home saat menu lain dipilih
            }

            Icon(
                painter = painterResource(id = fabIcon),
                contentDescription = if (selectedItem == 1) "Add" else "Home", // Deskripsi FAB berubah sesuai ikon
                modifier = Modifier.size(28.dp) // Ikon di dalam FAB lebih besar
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    TancikuTheme(dynamicColor = false) {
        MainBottomNavigation(items = listOf(
            BottomNavigationItem(icon = R.drawable.receipt, text = "Transaksi"),
            BottomNavigationItem(icon = R.drawable.home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.settings, text = "Pengaturan"),
        ), selectedItem = 0, onItemClick = {}, onFabClick = {})
    }
}