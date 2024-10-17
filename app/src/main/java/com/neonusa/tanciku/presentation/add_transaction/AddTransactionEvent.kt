package com.neonusa.tanciku.presentation.add_transaction

import com.neonusa.tanciku.domain.model.Transaction

sealed class AddTransactionEvent {
    data class InsertTransaction(val transaction: Transaction): AddTransactionEvent()
    object RemoveSideEffect: AddTransactionEvent()
}