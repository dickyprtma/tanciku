package com.neonusa.tanciku.presentation.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.neonusa.tanciku.domain.model.Transaction
import com.neonusa.tanciku.presentation.Dimens.MediumPadding1
import com.neonusa.tanciku.presentation.common.ListTransactionItem

@Composable
fun TransactionScreen(
    transactions: LazyPagingItems<Transaction>,
    onTransactionItemClicked: (Transaction) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
    ) {
        ListTransactionItem(
            transactions = transactions,
            onClick = {onTransactionItemClicked(it)}
        )
    }
}

//@Preview(showBackground = true)
//@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun TransactionScreenPreview(){
//    TransactionScreen()
//}