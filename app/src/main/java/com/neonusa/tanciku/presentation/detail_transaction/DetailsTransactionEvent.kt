package com.neonusa.tanciku.presentation.detail_transaction

import com.neonusa.tanciku.domain.model.Transaction

sealed class DetailsTransactionEvent {
    data class DeleteTransactionById(val id: Int): DetailsTransactionEvent()
}
