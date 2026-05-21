package com.luisfagundes.budget.di

import com.luisfagundes.budget.data.dao.ExpenseDao
import com.luisfagundes.budget.data.datasource.ExpenseLocalDataSource
import com.luisfagundes.budget.data.datasource.ExpenseLocalDataSourceImpl
import com.luisfagundes.budget.data.mapper.ExpenseMapper
import com.luisfagundes.common.data.database.TripDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BudgetDataModule {

    @Provides
    @Singleton
    fun provideExpenseDao(database: TripDatabase): ExpenseDao = database.expenseDao()

    @Provides
    @Singleton
    fun provideExpenseLocalDataSource(dao: ExpenseDao): ExpenseLocalDataSource =
        ExpenseLocalDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideExpenseMapper(): ExpenseMapper = ExpenseMapper()
}
