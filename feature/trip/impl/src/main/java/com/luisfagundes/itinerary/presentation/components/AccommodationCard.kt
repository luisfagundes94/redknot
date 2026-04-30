package com.luisfagundes.itinerary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.common.presentation.extensions.toAmPm
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.trip.R

@Composable
internal fun AccommodationCard(
    accommodation: Accommodation,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.verySmall)
    ) {
        val checkInTitle = if (accommodation.checkInType == CheckInType.CHECK_IN) {
            stringResource(R.string.hotel_check_in)
        } else {
            stringResource(R.string.hotel_check_out)
        }

        Text(
            text = checkInTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = accommodation.name,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = accommodation.time.toAmPm()
        )
        Text(
            text = accommodation.address
        )
    }
}