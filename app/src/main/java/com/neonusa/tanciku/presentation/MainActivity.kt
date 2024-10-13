package com.neonusa.tanciku.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neonusa.tanciku.presentation.home.HomeScreen
import com.neonusa.tanciku.presentation.navgraph.NavGraph
import com.neonusa.tanciku.ui.theme.TancikuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TancikuApp()
        }
    }
}

@Composable
fun TancikuApp() {
    TancikuTheme(dynamicColor = false) {
        val isSystemInDarkMode = isSystemInDarkTheme()
        val systemUiColor = rememberSystemUiController()
        ConfigureSystemBars(systemUiColor, isSystemInDarkMode)

        // Main UI content
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            NavGraph(startDestination = "BottomNavigation")
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

