package com.luisfagundes.budget.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.usecase.DeleteExpenseUseCase
import com.luisfagundes.budget.domain.usecase.GetBudgetSummaryUseCase
import com.luisfagundes.budget.domain.usecase.GetExpensesByCategoryUseCase
import com.luisfagundes.budget.domain.usecase.SetTotalBudgetUseCase
import com.luisfagundes.budget.presentation.viewmodel.effect.BudgetUiEffect
import com.luisfagundes.budget.presentation.viewmodel.state.BudgetUiState
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class BudgetViewModel @Inject constructor(
    private val getBudgetSummaryUseCase: GetBudgetSummaryUseCase,
    private val getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase,
    private val setTotalBudgetUseCase: SetTotalBudgetUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<BudgetUiState, BudgetUiEffect>(
    initialState = BudgetUiState.Loading
) {
    private val _showBudgetBottomSheet = MutableStateFlow(false)
    val showBudgetBottomSheet = _showBudgetBottomSheet.asStateFlow()

    fun loadBudget(tripId: Int) = viewModelScope.launch(dispatcher) {
        setState { BudgetUiState.Loading }
        getBudgetSummaryUseCase(tripId).fold(
            onSuccess = { budget ->
                if (budget == null) {
                    setState { BudgetUiState.Onboarding }
                } else {
                    getExpensesByCategoryUseCase(tripId).fold(
                        onSuccess = { expensesByCategory ->
                            setState { BudgetUiState.Dashboard(budget, expensesByCategory) }
                        },
                        onFailure = {
                            sendEffect { BudgetUiEffect.ShowErrorToast(it.toString()) }
                        }
                    )
                }
            },
            onFailure = {
                sendEffect { BudgetUiEffect.ShowErrorToast(it.toString()) }
            }
        )
    }

    fun onSetBudgetClick() {
        _showBudgetBottomSheet.value = true
    }

    fun onBudgetAmountConfirm(tripId: Int, amount: BigDecimal, currency: BudgetCurrency) =
        viewModelScope.launch(dispatcher) {
            _showBudgetBottomSheet.value = false
            setTotalBudgetUseCase(tripId, amount, currency).fold(
                onSuccess = { loadBudget(tripId) },
                onFailure = { sendEffect { BudgetUiEffect.ShowErrorToast(it.toString()) } }
            )
        }

    fun onDismissBottomSheet() {
        _showBudgetBottomSheet.value = false
    }

    fun onAddExpenseClick() {
        sendEffect { BudgetUiEffect.NavigateToAddExpense }
    }

    fun onDeleteExpense(tripId: Int, expense: Expense) = viewModelScope.launch(dispatcher) {
        deleteExpenseUseCase(tripId, expense).fold(
            onSuccess = { loadBudget(tripId) },
            onFailure = { sendEffect { BudgetUiEffect.ShowErrorToast(it.toString()) } }
        )
    }
}
