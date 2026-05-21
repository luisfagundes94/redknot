package com.luisfagundes.budget.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.presentation.components.AmountSection
import com.luisfagundes.budget.presentation.components.CategorySection
import com.luisfagundes.budget.presentation.provider.AddExpenseFormPreviewParameterProvider
import com.luisfagundes.budget.presentation.viewmodel.AddExpenseFormViewModel
import com.luisfagundes.budget.presentation.viewmodel.effect.AddExpenseFormUiEffect
import com.luisfagundes.budget.presentation.viewmodel.state.AddExpenseFormUiState
import com.luisfagundes.common.presentation.mapper.toMessage
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotDateSelectionField
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemeWrapper
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import java.math.BigDecimal
import java.time.LocalDate

@Composable
internal fun AddExpenseFormScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddExpenseFormViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is AddExpenseFormUiEffect.NavigateBack -> onNavigateBack()
            is AddExpenseFormUiEffect.ShowErrorToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    AddExpenseFormContent(
        uiState = uiState,
        onAmountChange = viewModel::onAmountChange,
        onCategorySelect = viewModel::onCategorySelect,
        onDateSelect = viewModel::onDateSelect,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSaveExpense = viewModel::onAddExpenseClick,
        onBackClick = viewModel::onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddExpenseFormContent(
    uiState: AddExpenseFormUiState,
    onAmountChange: (BigDecimal) -> Unit,
    onCategorySelect: (ExpenseCategory) -> Unit,
    onDateSelect: (LocalDate?) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveExpense: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.add_expense),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            AddExpenseFormBottomButtonBundle(
                isFormValid = uiState.isFormValid,
                onAddExpense = onSaveExpense,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        AddExpenseForm(
            uiState = uiState,
            onAmountChange = onAmountChange,
            onCategorySelect = onCategorySelect,
            onDateSelect = onDateSelect,
            onDescriptionChange = onDescriptionChange,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun AddExpenseFormBottomButtonBundle(
    isFormValid: Boolean,
    onAddExpense: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.default)
    ) {
        Button(
            onClick = onAddExpense,
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            Text(stringResource(R.string.add_expense))
        }
        TextButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cancel))
        }
    }
}

@Composable
private fun AddExpenseForm(
    uiState: AddExpenseFormUiState,
    onAmountChange: (BigDecimal) -> Unit,
    onCategorySelect: (ExpenseCategory) -> Unit,
    onDateSelect: (LocalDate?) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        AmountSection(
            amount = uiState.amount.toPlainString(),
            onAmountChange = { onAmountChange(it.toBigDecimal()) },
            modifier = Modifier.fillMaxWidth()
        )
        CategorySection(
            selectedCategory = uiState.selectedCategory,
            onCategorySelect = onCategorySelect,
            modifier = Modifier.fillMaxWidth()
        )
        RedknotDateSelectionField(
            date = uiState.selectedDate,
            label = stringResource(R.string.date_label),
            placeholder = stringResource(R.string.date_placeholder),
            hasError = uiState.selectedDateError != null,
            supportingText = {
                uiState.selectedDateError?.let { error ->
                    Text(error.toMessage(context))
                }
            },
            onDateSelect = onDateSelect,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.default)
        )
        OutlinedTextField(
            value = uiState.description,
            label = { Text(stringResource(R.string.optional_description)) },
            onValueChange = onDescriptionChange,
            placeholder = { Text(stringResource(R.string.expense_description_placeholder)) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@PreviewWrapper(RedknotThemeWrapper::class)
@RedknotPreview
@Composable
private fun AddExpenseFormContentPreview(
    @PreviewParameter(AddExpenseFormPreviewParameterProvider::class)
    uiState: AddExpenseFormUiState
) {
    AddExpenseFormContent(
        uiState = uiState,
        onAmountChange = {},
        onCategorySelect = {},
        onDateSelect = {},
        onDescriptionChange = {},
        onSaveExpense = {},
        onBackClick = {}
    )
}
