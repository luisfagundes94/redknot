package com.luisfagundes.budget.domain.usecase

import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.repository.BudgetRepository
import java.math.BigDecimal
import javax.inject.Inject

internal class SetTotalBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(
        tripId: Int,
        totalBudget: BigDecimal,
        currency: BudgetCurrency
    ): Result<Unit> = runCatching {
        repository.setTotalBudget(tripId, totalBudget).getOrThrow()
        repository.setCurrency(tripId, currency).getOrThrow()
    }
}
