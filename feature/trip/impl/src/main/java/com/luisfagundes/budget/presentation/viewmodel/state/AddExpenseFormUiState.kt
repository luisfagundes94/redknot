package com.luisfagundes.budget.presentation.viewmodel.state

import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.math.BigDecimal
import java.time.LocalDate

internal data class AddExpenseFormUiState(
    val amount: BigDecimal = BigDecimal(0.00),
    val selectedCategory: ExpenseCategory = ExpenseCategory.TRANSPORT,
    val selectedDate: LocalDate? = LocalDate.now(),
    val selectedDateError: FieldValidationError? = null,
    val description: String = "",
) : UiState {
    val isFormValid: Boolean
        get() = listOf(selectedDateError).all { it == null }
}
