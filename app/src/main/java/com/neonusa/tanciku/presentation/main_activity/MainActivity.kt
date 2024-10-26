package com.neonusa.tanciku.presentation.main_activity

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neonusa.tanciku.data.local.TransactionDao
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.domain.model.TransactionCategory
import com.neonusa.tanciku.domain.model.TransactionType
import com.neonusa.tanciku.presentation.navgraph.NavGraph
import com.neonusa.tanciku.presentation.navgraph.Route
import com.neonusa.tanciku.ui.theme.TancikuTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

//todo: bikin dialog meyakinkan apakah ingin menghapus transaksi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // todo : for test only please remove after test
    @Inject
    lateinit var transactionDao: TransactionDao

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo : TESTING ROOM
        // Daftar deskripsi dan kategori untuk digunakan sebagai data dummy
        val descriptions = listOf(
            "Beli ayam geprek", "Beli kopi", "Beli pulsa", "Beli baju", "Beli buku",
            "Bayar listrik", "Bayar internet", "Belanja mingguan", "Isi bensin", "Nonton bioskop"
        )
        val categories = listOf(
            TransactionCategory.Kebutuhan,
            TransactionCategory.Keinginan,
            TransactionCategory.Menabung,
            TransactionCategory.Pemasukan
        )
        val types = listOf(
            TransactionType.Pengeluaran,
            TransactionType.Pemasukan
        )

        // Fungsi untuk membuat tanggal acak dalam rentang tahun 2024
        fun randomDate(): String {
            val month = Random.nextInt(1, 13).toString().padStart(2, '0')
            val day = Random.nextInt(1, 29).toString().padStart(2, '0') // Anggap Februari hanya 28 hari
            return "2024-$month-$day"
        }

        // Fungsi untuk membuat jumlah acak
        fun randomAmount(): Int {
            return Random.nextInt(5000, 100000) // Nilai antara 5.000 hingga 100.000
        }

// Membuat data dummy di dalam lifecycleScope
        lifecycleScope.launch {
            repeat(12) { index ->
                val description = descriptions.random() + " $index"
                val date = randomDate()
                val type = types.random()
                val category = categories.random()
                val amount = randomAmount()

                transactionDao.insert(
                    Transaction(
                        description = description,
                        date = date,
                        type = type,
                        category = category,
                        amount = amount
                    )
                )
            }
        }

        //========================================

        installSplashScreen().apply {
            setKeepOnScreenCondition(
                condition = { viewModel.splashCondition.value || viewModel.startDestination.value.isEmpty()}
            )
        }


        enableEdgeToEdge()
        setContent {
            TancikuApp()
        }
    }
}

@Composable
fun TancikuApp(viewModel: MainViewModel = hiltViewModel()) {
    TancikuTheme(dynamicColor = false) {
        val isSystemInDarkMode = isSystemInDarkTheme()
        val systemUiColor = rememberSystemUiController()
        ConfigureSystemBars(systemUiColor, isSystemInDarkMode)

        if (viewModel.startDestination.value.isNotEmpty()) {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                NavGraph(startDestination = viewModel.startDestination.value)
            }
        }
    }
}

@Composable
fun ConfigureSystemBars(systemUiColor: SystemUiController, isSystemInDarkMode: Boolean) {
    SideEffect {
        systemUiColor.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isSystemInDarkMode
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScreen() {
    TancikuApp()
}

