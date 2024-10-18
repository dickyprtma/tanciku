package com.neonusa.tanciku.di

import android.app.Application
import androidx.room.Room
import com.neonusa.tanciku.data.TransactionRepositoryImpl
import com.neonusa.tanciku.data.local.TransactionConverter
import com.neonusa.tanciku.data.local.TransactionDao
import com.neonusa.tanciku.data.local.TransactionDatabase
import com.neonusa.tanciku.data.manager.LocalUserManagerImpl
import com.neonusa.tanciku.domain.manager.LocalUserManager
import com.neonusa.tanciku.domain.repository.TransactionRepository
import com.neonusa.tanciku.domain.usecases.app_entry.AppEntryUseCases
import com.neonusa.tanciku.domain.usecases.app_entry.ReadAppEntry
import com.neonusa.tanciku.domain.usecases.app_entry.SaveAppEntry
import com.neonusa.tanciku.domain.usecases.transaction.TransactionUseCases
import com.neonusa.tanciku.domain.usecases.transaction.DeleteTransaction
import com.neonusa.tanciku.domain.usecases.transaction.DeleteTransactionById
import com.neonusa.tanciku.domain.usecases.transaction.GetTotalExpense
import com.neonusa.tanciku.domain.usecases.transaction.GetTotalIncome
import com.neonusa.tanciku.domain.usecases.transaction.InsertTransaction
import com.neonusa.tanciku.utils.Constants.DATABASE_NAME
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
            deleteTransactionById = DeleteTransactionById(transactionRepository),
            getTotalIncome = GetTotalIncome((transactionRepository)),
            getTotalExpense = GetTotalExpense(transactionRepository)
        )
    }

    @Provides
    @Singleton
    fun provideTransactionDatabase(
        application: Application
    ): TransactionDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = TransactionDatabase::class.java,
            name = DATABASE_NAME
        ).addTypeConverter(TransactionConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(
        transactionDatabase: TransactionDatabase
    ): TransactionDao = transactionDatabase.transactionDao

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )
}