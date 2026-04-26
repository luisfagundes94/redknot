package com.luisfagundes.budget.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface AddExpenseFormUiEffect : UiEffect {
    data object NavigateBack : AddExpenseFormUiEffect
    data class ShowErrorToast(val message: String) : AddExpenseFormUiEffect
}
