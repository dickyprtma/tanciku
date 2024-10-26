package com.neonusa.tanciku.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.neonusa.tanciku.domain.model.Transaction

class TransactionPagingSource(
    private val transactionDao: TransactionDao
): PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalTransactionCount = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            // Fetch a subset of transactions based on page and page size
            val transactions = transactionDao.getTransactionsForPage(page, pageSize)

            LoadResult.Page(
                data = transactions,
                nextKey = if (transactions.size < pageSize) null else page + 1,
                prevKey = if (page == 1) null else page - 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}


//class NewsPagingSource(
//    private val newsApi: NewsApi,
//    private val sources: String
//) : PagingSource<Int, Article>()