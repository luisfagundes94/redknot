package com.luisfagundes.itinerary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.common.presentation.toAmPm
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.Restaurant

@Composable
internal fun RestaurantCard(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.verySmall)
    ) {
        Text(
            text = "Lunch at ${restaurant.name}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = restaurant.time.toAmPm()
        )
        Text(
            text = restaurant.address
        )
    }
}