package com.luisfagundes.budget.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.luisfagundes.budget.domain.model.Budget
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import java.math.BigDecimal

private val ColorGreen = Color(0xFF4CAF50)
private val ColorYellow = Color(0xFFFFC107)
private val ColorRed = Color(0xFFF44336)

private const val PROGRESS_THRESHOLD_LOW = 0.5f
private const val PROGRESS_THRESHOLD_MEDIUM = 0.85f

@Composable
internal fun BudgetOverviewCard(
    budget: Budget,
    modifier: Modifier = Modifier
) {
    val progress = remember(budget) {
        if (budget.total <= BigDecimal.ZERO) 0f
        else (budget.spent.toFloat() / budget.total.toFloat()).coerceIn(0f, 1f)
    }
    val progressColor = when {
        progress < PROGRESS_THRESHOLD_LOW -> ColorGreen
        progress < PROGRESS_THRESHOLD_MEDIUM -> ColorYellow
        else -> ColorRed
    }
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = stringResource(R.string.total_budget),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "${budget.currency.symbol}${budget.total.toPlainString()}",
                style = MaterialTheme.typography.headlineMedium
            )
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = progressColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                drawStopIndicator = {}
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.spent),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "${budget.currency.symbol}${budget.spent.toPlainString()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column {
                    Text(
                        text = stringResource(R.string.remaining),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "${budget.currency.symbol}${budget.remaining.toPlainString()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
