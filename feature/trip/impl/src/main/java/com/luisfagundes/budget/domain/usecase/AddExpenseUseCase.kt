package com.luisfagundes.budget.domain.usecase

import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.repository.BudgetRepository
import javax.inject.Inject

internal class AddExpenseUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(expense: Expense) = repository.addExpense(expense)
}