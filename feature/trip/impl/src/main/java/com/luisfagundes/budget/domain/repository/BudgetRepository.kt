package com.luisfagundes.budget.domain.repository

import com.luisfagundes.budget.domain.model.Expense

internal interface BudgetRepository {
    fun getExpenses(): Result<List<Expense>>
    fun addExpense(expense: Expense): Result<Unit>
}