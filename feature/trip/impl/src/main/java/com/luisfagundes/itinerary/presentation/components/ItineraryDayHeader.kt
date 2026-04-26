package com.luisfagundes.itinerary.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val DAY_HEADER_FORMATTER: DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEEE, MMM d")

@Composable
internal fun ItineraryDayHeader(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    Text(
        text = date.format(DAY_HEADER_FORMATTER),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}
