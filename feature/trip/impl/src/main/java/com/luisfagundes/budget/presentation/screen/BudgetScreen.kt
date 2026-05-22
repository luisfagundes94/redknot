package com.luisfagundes.budget.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.presentation.components.BudgetOverviewCard
import com.luisfagundes.budget.presentation.components.ExpenseListItem
import com.luisfagundes.budget.presentation.components.SetBudgetBottomSheet
import com.luisfagundes.budget.presentation.viewmodel.BudgetViewModel
import com.luisfagundes.budget.presentation.viewmodel.effect.BudgetUiEffect
import com.luisfagundes.budget.presentation.viewmodel.state.BudgetUiState
import com.luisfagundes.common.presentation.extensions.paddingExceptBottom
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotEmptyTemplate
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R

@Composable
internal fun BudgetScreen(
    tripId: Int,
    onNavigateToAddExpense: () -> Unit,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showBottomSheet by viewModel.showBudgetBottomSheet.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadBudget(tripId)
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is BudgetUiEffect.NavigateToAddExpense -> onNavigateToAddExpense()
            is BudgetUiEffect.ShowErrorToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (showBottomSheet) {
        SetBudgetBottomSheet(
            onConfirm = { amount, currency ->
                viewModel.onBudgetAmountConfirm(
                    tripId,
                    amount,
                    currency
                )
            },
            onDismiss = viewModel::onDismissBottomSheet
        )
    }

    BudgetContent(
        uiState = uiState,
        onSetBudgetClick = viewModel::onSetBudgetClick,
        onAddExpenseClick = viewModel::onAddExpenseClick,
        onDeleteExpense = { expense -> viewModel.onDeleteExpense(tripId, expense) }
    )
}

@Composable
private fun BudgetContent(
    uiState: BudgetUiState,
    onSetBudgetClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onDeleteExpense: (Expense) -> Unit
) {
    when (uiState) {
        is BudgetUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )

        is BudgetUiState.Onboarding -> RedknotEmptyTemplate(
            title = stringResource(R.string.budget_onboarding_message),
            primaryButtonLabel = stringResource(R.string.set_budget),
            primaryButtonIcon = Icons.Default.AttachMoney,
            onPrimaryButtonClick = onSetBudgetClick,
            modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.default)
        )

        is BudgetUiState.Dashboard -> BudgetDashboardContent(
            uiState = uiState,
            currencySymbol = uiState.budget.currency.symbol,
            onAddExpenseClick = onAddExpenseClick,
            onDeleteExpense = { expense -> onDeleteExpense(expense) },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun BudgetDashboardContent(
    uiState: BudgetUiState.Dashboard,
    currencySymbol: String,
    onAddExpenseClick: () -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpenseClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_expense)
                )
            }
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            item {
                BudgetOverviewCard(
                    budget = uiState.budget,
                    modifier = Modifier
                        .fillMaxWidth()
                        .paddingExceptBottom(MaterialTheme.spacing.default)
                )
            }
            if (uiState.expensesByCategory.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_expenses_yet),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                uiState.expensesByCategory.forEach { (category, expenses) ->
                    item(key = category.name) {
                        ExpenseCategorySection(
                            category = category,
                            expenses = expenses,
                            currencySymbol = currencySymbol,
                            onDeleteExpense = onDeleteExpense,
                            modifier = Modifier.fillParentMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpenseCategorySection(
    category: ExpenseCategory,
    expenses: List<Expense>,
    currencySymbol: String,
    onDeleteExpense: (Expense) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default)
        )
        expenses.forEach { expense ->
            SwipeToDeleteExpenseItem(
                expense = expense,
                currencySymbol = currencySymbol,
                onDelete = { onDeleteExpense(expense) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteExpenseItem(
    expense: Expense,
    currencySymbol: String,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { totalDistance -> totalDistance * 0.2f }
    )

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onDelete()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_expense),
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(end = MaterialTheme.spacing.default)
                )
            }
        },
        modifier = modifier
    ) {
        ExpenseListItem(
            expense = expense,
            currencySymbol = currencySymbol,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.small
                )
        )
    }
}
