package com.neonusa.tanciku.presentation.navigator

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)