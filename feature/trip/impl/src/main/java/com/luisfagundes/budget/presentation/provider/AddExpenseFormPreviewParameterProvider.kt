package com.luisfagundes.budget.presentation.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.presentation.viewmodel.state.AddExpenseFormUiState
import java.time.LocalDate
import java.time.Month

internal class AddExpenseFormPreviewParameterProvider :
    PreviewParameterProvider<AddExpenseFormUiState> {
    override val values = sequenceOf(
        AddExpenseFormUiState(
            amountText = "42.00",
            selectedCategory = ExpenseCategory.TRANSPORT,
            selectedDate = LocalDate.of(2026, Month.APRIL, 15),
            description = "Train tickets to Paris"
        )
    )
}
