package com.luisfagundes.budget.presentation.viewmodel.state

import com.luisfagundes.budget.domain.model.Budget
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.core.common.presentation.arch.state.UiState

internal sealed interface BudgetUiState : UiState {
    data object Loading : BudgetUiState
    data object Onboarding : BudgetUiState
    data class Dashboard(
        val budget: Budget,
        val expensesByCategory: Map<ExpenseCategory, List<Expense>>
    ) : BudgetUiState
}
