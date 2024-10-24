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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neonusa.tanciku.presentation.navgraph.NavGraph
import com.neonusa.tanciku.presentation.navgraph.Route
import com.neonusa.tanciku.ui.theme.TancikuTheme
import dagger.hilt.android.AndroidEntryPoint

//todo : masih bug menampilkan getstarted screen waktu buka aplikasi  (padahal sudah save entry)
// todo : bug kalau klik icon edit dua kali, dia ke halaman edit juga dua kali (dua halaman ngestack)

//todo: bikin dialog meyakinkan apakah ingin menghapus transaksi
//todo : fitur edit transaksi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // original code
//        installSplashScreen().apply {
//            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
//        }

        var isConditionMet = false
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{
            // Logika untuk tetap menampilkan splash screen sampai kondisi tertentu terpenuhi
            // selama ini bernilai true, splashscreen akan terus tampil
            Log.d("MainActivity@test", "onCreate: splash screen showing...$isConditionMet")
            !isConditionMet
        }

        // Menggunakan Handler untuk menunda perubahan kondisi selama dua detik
        Handler(Looper.getMainLooper()).postDelayed({
            isConditionMet = true
            Log.d("MainActivity@test", "onCreate: here your first screen...$isConditionMet")
        }, 2000)

        enableEdgeToEdge()
        setContent {
//            TancikuApp()
        }
    }
}

@Composable
fun TancikuApp(viewModel: MainViewModel = hiltViewModel()) {
    TancikuTheme(dynamicColor = false) {
        val isSystemInDarkMode = isSystemInDarkTheme()
        val systemUiColor = rememberSystemUiController()
        ConfigureSystemBars(systemUiColor, isSystemInDarkMode)

        // Main UI content
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            NavGraph(startDestination = viewModel.startDestination.value)
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

