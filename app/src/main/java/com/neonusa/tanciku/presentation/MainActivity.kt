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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neonusa.tanciku.presentation.home.HomeScreen
import com.neonusa.tanciku.ui.theme.TancikuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TancikuTheme(dynamicColor = false) {
                // THEME
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemUiColor = rememberSystemUiController()
                SideEffect {
                    systemUiColor.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScreen() {
    TancikuTheme(dynamicColor = false) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            HomeScreen()
        }
    }
}

