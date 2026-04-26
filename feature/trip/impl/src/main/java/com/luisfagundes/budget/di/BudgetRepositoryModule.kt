package com.luisfagundes.budget.di

import com.luisfagundes.budget.data.repository.BudgetRepositoryImpl
import com.luisfagundes.budget.domain.repository.BudgetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BudgetRepositoryModule {
    @Singleton
    @Binds
    abstract fun provideBudgetRepository(
        impl: BudgetRepositoryImpl
    ): BudgetRepository
}