package com.luisfagundes.budget.domain.usecase

import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.repository.BudgetRepository
import javax.inject.Inject

internal class DeleteExpenseUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(tripId: Int, expense: Expense): Result<Unit> =
        repository.deleteExpense(tripId, expense)
}
