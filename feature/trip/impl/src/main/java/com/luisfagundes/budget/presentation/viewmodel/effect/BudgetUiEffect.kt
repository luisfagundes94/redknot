package com.luisfagundes.budget.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface BudgetUiEffect : UiEffect {
    data object NavigateToAddExpense : BudgetUiEffect
    data class ShowErrorToast(val message: String) : BudgetUiEffect
}
