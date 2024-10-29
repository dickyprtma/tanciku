package com.neonusa.tanciku.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.neonusa.tanciku.BuildConfig

@Composable
fun AdMobBannerAd(
    modifier: Modifier = Modifier,
    adUnitId: String = BuildConfig.MAIN_BANNER, // Test ad unit ID. Replace with your own.
    adSize: AdSize = AdSize.BANNER
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier), // Preserve the custom modifier if provided
        contentAlignment = Alignment.Center // Center the content horizontally
    ) {
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                AdView(context).apply {
                    setAdSize(adSize)
                    this.adUnitId = adUnitId
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}