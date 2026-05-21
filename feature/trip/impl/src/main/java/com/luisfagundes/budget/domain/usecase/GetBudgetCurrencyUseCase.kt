package com.luisfagundes.budget.domain.usecase

import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.repository.BudgetRepository
import javax.inject.Inject

internal class GetBudgetCurrencyUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(tripId: Int): Result<BudgetCurrency> =
        repository.getCurrency(tripId)
}
