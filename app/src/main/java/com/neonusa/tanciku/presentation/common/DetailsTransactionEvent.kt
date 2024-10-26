package com.neonusa.tanciku.presentation.common

import com.neonusa.tanciku.domain.model.Transaction

sealed class DetailsTransactionEvent {
    data class DeleteTransactionById(val id: Int): DetailsTransactionEvent()
    data class EditTransaction(val transaction: Transaction): DetailsTransactionEvent()
}
