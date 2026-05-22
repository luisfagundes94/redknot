package com.luisfagundes.budget.presentation.viewmodel.event

import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import java.time.LocalDate

internal sealed interface AddExpenseFormUiEvent : UiEvent {
    data class SetTripId(val tripId: Int) : AddExpenseFormUiEvent
    data class UpdateAmount(val amount: String) : AddExpenseFormUiEvent
    data class UpdateCategory(val category: ExpenseCategory) : AddExpenseFormUiEvent
    data class UpdateDate(val date: LocalDate?) : AddExpenseFormUiEvent
    data class UpdateDescription(val description: String) : AddExpenseFormUiEvent
    data object AddExpense : AddExpenseFormUiEvent
    data object NavigateBack : AddExpenseFormUiEvent
}
