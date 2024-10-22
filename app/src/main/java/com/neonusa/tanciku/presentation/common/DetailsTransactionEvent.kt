package com.neonusa.tanciku.presentation.common

sealed class DetailsTransactionEvent {
    data class DeleteTransactionById(val id: Int): DetailsTransactionEvent()
}
