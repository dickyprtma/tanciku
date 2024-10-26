package com.neonusa.tanciku.presentation.edit_transaction

import com.neonusa.tanciku.domain.model.Transaction

sealed class EditTransactionEvent {
    data class EditTransaction(val transaction: Transaction): EditTransactionEvent()
    object RemoveSideEffect: EditTransactionEvent()
}