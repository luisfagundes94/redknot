package com.luisfagundes.budget.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luisfagundes.budget.domain.model.ExpenseCategory
import com.luisfagundes.budget.presentation.mapper.toIcon
import com.luisfagundes.common.presentation.toTitleCase
import com.luisfagundes.designsystem.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CategorySection(
    selectedCategory: ExpenseCategory,
    onCategorySelect: (ExpenseCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.default)) {
            Text(
                text = "CATEGORY",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                ExpenseCategory.entries.forEach { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { onCategorySelect(category) },
                        label = { Text(category.name.toTitleCase()) },
                        leadingIcon = {
                            Icon(
                                imageVector = category.toIcon(),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}