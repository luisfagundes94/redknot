package com.luisfagundes.documents.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.presentation.mapper.toIcon
import com.luisfagundes.documents.presentation.mapper.toTitle
import com.luisfagundes.trip.R

@Composable
internal fun DocumentCategorySection(
    selectedCategory: DocumentCategory,
    onCategorySelect: (DocumentCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.default)) {
            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                DocumentCategory.entries.forEach { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { onCategorySelect(category) },
                        label = { Text(category.toTitle(LocalContext.current)) },
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