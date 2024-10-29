package com.neonusa.tanciku.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import com.neonusa.tanciku.presentation.common.DetailsTransactionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {

    private var _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
            }

            is SearchEvent.SearchNews -> {
                searchNews()
            }
        }
    }

    // overloading
    fun onEvent(event: DetailsTransactionEvent){
        when(event){
            is DetailsTransactionEvent.DeleteTransactionById -> {
                viewModelScope.launch {
                    deleteTransactionById(event.id)
                }
            }
            is  DetailsTransactionEvent.EditTransaction -> {
                viewModelScope.launch {

                }
            }
        }
    }

    private fun searchNews() {
        val transactions = transactionUseCases.searchTransaction(
            query = _state.value.searchQuery,
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(transactions = transactions)
    }

    private suspend fun deleteTransactionById(id: Int){
        transactionUseCases.deleteTransactionById(id)
    }
}