package com.luisfagundes.budget.domain.usecase

import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.domain.repository.BudgetRepository
import javax.inject.Inject

internal class GetExpensesByCategoryUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(tripId: Int): Result<Map<ExpenseCategory, List<Expense>>> =
        repository.getExpensesByTripId(tripId).map { expenses ->
            expenses.groupBy { it.category }
        }
}
