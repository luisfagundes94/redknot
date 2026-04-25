package com.luisfagundes.itinerary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.presentation.extensions.getTimeRange
import com.luisfagundes.trip.R

@Composable
internal fun FlightCard(
    flight: Flight,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.verySmall)
    ) {
        Text(
            text = stringResource(R.string.flight_to, flight.destination.city),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = flight.getTimeRange()
        )
        Text(
            text = "${flight.companyName} (${flight.flightNumber})"
        )
    }
}