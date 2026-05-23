package com.luisfagundes.budget.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.presentation.extensions.toFormattedString
import com.luisfagundes.budget.presentation.mapper.toIcon
import com.luisfagundes.common.presentation.extensions.toTitleCase
import com.luisfagundes.designsystem.theme.spacing

@Composable
internal fun ExpenseListItem(
    expense: Expense,
    currencySymbol: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = expense.category.toIcon(),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = expense.description.orEmpty().ifEmpty {
                    expense.category.name.toTitleCase()
                },
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = expense.date.toFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "-$currencySymbol${expense.amount.toPlainString()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}
