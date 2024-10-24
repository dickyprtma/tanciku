package com.neonusa.tanciku

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        // untuk menggunakan library threetenabp (convert format date)
        AndroidThreeTen.init(this)
        Log.d("Tag", "onCreate: initialized")


        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@App) {}
        }
    }
}