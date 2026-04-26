package com.luisfagundes.trip.presentation.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.presentation.mapper.toStringResId
import com.luisfagundes.trip.tools.extensions.formatTripPeriod
import com.luisfagundes.trip.tools.extensions.getTripDurationInDays
import java.time.LocalDate

@Composable
internal fun TripDetailsHeader(
    status: TripStatus,
    startDate: LocalDate,
    endDate: LocalDate,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(status.toStringResId()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(MaterialTheme.spacing.default)
                )
                .padding(horizontal = MaterialTheme.spacing.small)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.small)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.small)
            )
            Text(
                text = formatTripPeriod(startDate, endDate),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.dot),
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
            )
            Text(
                text = pluralStringResource(
                    id = R.plurals.trip_duration_days,
                    count = getTripDurationInDays(startDate, endDate),
                    getTripDurationInDays(startDate, endDate)
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }
}