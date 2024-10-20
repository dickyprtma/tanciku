package com.neonusa.tanciku

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        // untuk menggunakan library threetenabp (convert format date)
        AndroidThreeTen.init(this);
    }
}