package com.luisfagundes.budget.presentation.viewmodel.state

import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.math.BigDecimal
import java.time.LocalDate

internal data class AddExpenseFormUiState(
    val amountText: String = "",
    val currencySymbol: String = BudgetCurrency.EUR.symbol,
    val selectedCategory: ExpenseCategory = ExpenseCategory.TRANSPORT,
    val selectedDate: LocalDate? = LocalDate.now(),
    val selectedDateError: FieldValidationError? = null,
    val description: String = "",
) : UiState {
    val parsedAmount: BigDecimal get() = amountText.toBigDecimalOrNull() ?: BigDecimal.ZERO

    val isFormValid: Boolean
        get() = parsedAmount > BigDecimal.ZERO && listOf(selectedDateError).all { it == null }
}
