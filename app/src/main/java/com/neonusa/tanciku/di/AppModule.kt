package com.neonusa.tanciku.di

import com.neonusa.tanciku.data.TransactionRepositoryImpl
import com.neonusa.tanciku.data.local.TransactionDao
import com.neonusa.tanciku.data.local.TransactionDatabase
import com.neonusa.tanciku.domain.repository.TransactionRepository
import com.neonusa.tanciku.domain.usecases.TransactionUseCases
import com.neonusa.tanciku.domain.usecases.transaction.DeleteTransaction
import com.neonusa.tanciku.domain.usecases.transaction.InsertTransaction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTransactionDao(
        transactionDatabase: TransactionDatabase
    ): TransactionDao = transactionDatabase.transactionDao

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }

    @Provides
    @Singleton
    fun provideTransactionUseCases(
        transactionRepository: TransactionRepository
    ): TransactionUseCases {
        return TransactionUseCases(
            insertTransaction = InsertTransaction(transactionRepository),
            deleteTransaction = DeleteTransaction(transactionRepository),
        )
    }
}