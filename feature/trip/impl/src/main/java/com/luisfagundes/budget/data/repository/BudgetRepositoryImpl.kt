package com.luisfagundes.budget.data.repository

import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.repository.BudgetRepository
import javax.inject.Inject

internal class BudgetRepositoryImpl @Inject constructor(): BudgetRepository {
    override fun getExpenses(): Result<List<Expense>> {
        TODO("Not yet implemented")
    }

    override fun addExpense(expense: Expense): Result<Unit> {
        TODO("Not yet implemented")
    }
}