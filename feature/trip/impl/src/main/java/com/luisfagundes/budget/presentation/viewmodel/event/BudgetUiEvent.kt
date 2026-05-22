package com.luisfagundes.budget.presentation.viewmodel.event

import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import java.math.BigDecimal

internal sealed interface BudgetUiEvent : UiEvent {
    data class LoadBudget(val tripId: Int) : BudgetUiEvent
    data object OpenSetBudget : BudgetUiEvent
    data class ConfirmBudgetAmount(
        val tripId: Int,
        val amount: BigDecimal,
        val currency: BudgetCurrency
    ) : BudgetUiEvent
    data object DismissBudgetBottomSheet : BudgetUiEvent
    data object NavigateToAddExpense : BudgetUiEvent
    data class DeleteExpense(val tripId: Int, val expense: Expense) : BudgetUiEvent
}
