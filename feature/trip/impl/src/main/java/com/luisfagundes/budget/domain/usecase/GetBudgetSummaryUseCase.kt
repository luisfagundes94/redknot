package com.luisfagundes.budget.domain.usecase

import com.luisfagundes.budget.domain.model.Budget
import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.repository.BudgetRepository
import java.math.BigDecimal
import javax.inject.Inject

internal class GetBudgetSummaryUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(tripId: Int): Result<Budget?> = runCatching {
        val totalBudget = repository.getTotalBudget(tripId).getOrThrow() ?: return@runCatching null
        val expenses = repository.getExpensesByTripId(tripId).getOrThrow()
        val currency = repository.getCurrency(tripId).getOrElse { BudgetCurrency.EUR }
        val spent = expenses.fold(BigDecimal.ZERO) { acc, expense -> acc + expense.amount }
        Budget(total = totalBudget, spent = spent, remaining = totalBudget - spent, currency = currency)
    }
}
