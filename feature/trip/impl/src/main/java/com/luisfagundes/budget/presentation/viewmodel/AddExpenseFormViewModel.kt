package com.luisfagundes.budget.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.domain.usecase.AddExpenseUseCase
import com.luisfagundes.budget.presentation.viewmodel.effect.AddExpenseFormUiEffect
import com.luisfagundes.budget.presentation.viewmodel.state.AddExpenseFormUiState
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.trip.domain.usecase.ValidateDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AddExpenseFormViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<AddExpenseFormUiState, AddExpenseFormUiEffect>(
    initialState = AddExpenseFormUiState()
) {
    fun onAmountChange(amount: BigDecimal) {
        setState { it.copy(amount = amount) }
    }

    fun onCategorySelect(category: ExpenseCategory) {
        setState { it.copy(selectedCategory = category) }
    }

    fun onDateSelect(date: LocalDate?) {
        setState { it.copy(selectedDate = date, selectedDateError = validateDateUseCase(date)) }
    }

    fun onDescriptionChange(description: String) {
        setState { it.copy(description = description) }
    }

    fun onAddExpenseClick() = viewModelScope.launch(dispatcher) {
        addExpenseUseCase(createExpense()).fold(
            onSuccess = { sendEffect { AddExpenseFormUiEffect.NavigateBack } },
            onFailure = { sendEffect { AddExpenseFormUiEffect.ShowErrorToast(it.toString()) }}
        )
    }

    fun onBackClick() {
        sendEffect { AddExpenseFormUiEffect.NavigateBack }
    }
    
    private fun createExpense(): Expense {
        val state = getCurrentState()

        return Expense(
            amount = state.amount,
            category = state.selectedCategory,
            date = state.selectedDate ?: LocalDate.now(),
            description = state.description
        )
    }
}
