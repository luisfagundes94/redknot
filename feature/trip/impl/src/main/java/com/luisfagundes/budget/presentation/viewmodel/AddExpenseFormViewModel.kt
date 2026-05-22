package com.luisfagundes.budget.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.domain.usecase.AddExpenseUseCase
import com.luisfagundes.budget.domain.usecase.GetBudgetCurrencyUseCase
import com.luisfagundes.budget.presentation.viewmodel.effect.AddExpenseFormUiEffect
import com.luisfagundes.budget.presentation.viewmodel.event.AddExpenseFormUiEvent
import com.luisfagundes.budget.presentation.viewmodel.state.AddExpenseFormUiState
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AddExpenseFormViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val getBudgetCurrencyUseCase: GetBudgetCurrencyUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<AddExpenseFormUiState, AddExpenseFormUiEvent, AddExpenseFormUiEffect>(
    initialState = AddExpenseFormUiState()
) {
    private var tripId: Int = 0

    override fun dispatchEvent(event: AddExpenseFormUiEvent) {
        when (event) {
            is AddExpenseFormUiEvent.SetTripId -> setTripId(event.tripId)
            is AddExpenseFormUiEvent.UpdateAmount -> updateAmount(event.amount)
            is AddExpenseFormUiEvent.UpdateCategory -> updateCategory(event.category)
            is AddExpenseFormUiEvent.UpdateDate -> updateDate(event.date)
            is AddExpenseFormUiEvent.UpdateDescription -> updateDescription(event.description)
            is AddExpenseFormUiEvent.AddExpense -> addExpense()
            is AddExpenseFormUiEvent.NavigateBack -> navigateBack()
        }
    }

    private fun setTripId(tripId: Int) {
        this.tripId = tripId
        viewModelScope.launch(dispatcher) {
            getBudgetCurrencyUseCase(tripId).fold(
                onSuccess = { currency ->
                    setState { currentState -> currentState.copy(currencySymbol = currency.symbol) }
                },
                onFailure = { /* keep default EUR */ }
            )
        }
    }

    private fun updateAmount(text: String) {
        setState { currentState ->
            currentState.copy(amountText = text)
        }
    }

    private fun updateCategory(category: ExpenseCategory) {
        setState { currentState ->
            currentState.copy(selectedCategory = category)
        }
    }

    private fun updateDate(date: LocalDate?) {
        setState { currentState ->
            currentState.copy(
                selectedDate = date,
                selectedDateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    private fun updateDescription(description: String) {
        setState { currentState ->
            currentState.copy(description = description)
        }
    }

    private fun addExpense() = viewModelScope.launch(dispatcher) {
        addExpenseUseCase(tripId, createExpense()).fold(
            onSuccess = { sendEffect { AddExpenseFormUiEffect.NavigateBack } },
            onFailure = { sendEffect { AddExpenseFormUiEffect.ShowErrorToast(it.toString()) }}
        )
    }

    private fun navigateBack() {
        sendEffect { AddExpenseFormUiEffect.NavigateBack }
    }

    private fun createExpense(): Expense {
        val state = getCurrentState()

        return Expense(
            amount = state.parsedAmount,
            category = state.selectedCategory,
            date = state.selectedDate ?: LocalDate.now(),
            description = state.description
        )
    }
}
