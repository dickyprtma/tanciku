package com.neonusa.tanciku.presentation.home

import com.neonusa.tanciku.domain.model.Transaction


data class HomeState(
    val transactions: List<Transaction> = emptyList()
)
