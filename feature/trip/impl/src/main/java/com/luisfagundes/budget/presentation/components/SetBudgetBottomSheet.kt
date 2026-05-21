package com.luisfagundes.budget.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import java.math.BigDecimal

private val currencySaver = Saver<BudgetCurrency, String>(
    save = { it.name },
    restore = { name -> BudgetCurrency.entries.firstOrNull { it.name == name } ?: BudgetCurrency.EUR }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetBudgetBottomSheet(
    onConfirm: (BigDecimal, BudgetCurrency) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var amountText by rememberSaveable { mutableStateOf("") }
    var selectedCurrency by rememberSaveable(stateSaver = currencySaver) {
        mutableStateOf(BudgetCurrency.EUR)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.default)
                .padding(bottom = MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            Text(
                text = stringResource(R.string.set_budget_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.currency_picker_label),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                items(BudgetCurrency.entries) { currency ->
                    FilterChip(
                        selected = currency == selectedCurrency,
                        onClick = { selectedCurrency = currency },
                        label = { Text("${currency.symbol} ${currency.code}") }
                    )
                }
            }
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text(stringResource(R.string.total_budget)) },
                placeholder = { Text(stringResource(R.string.budget_amount_placeholder)) },
                prefix = { Text(selectedCurrency.symbol) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val amount = amountText.toBigDecimalOrNull() ?: BigDecimal.ZERO
                    if (amount > BigDecimal.ZERO) onConfirm(amount, selectedCurrency)
                },
                enabled = amountText.toBigDecimalOrNull()?.let { it > BigDecimal.ZERO } == true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.confirm))
            }
        }
    }
}
