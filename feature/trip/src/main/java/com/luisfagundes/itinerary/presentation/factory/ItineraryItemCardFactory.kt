package com.luisfagundes.itinerary.presentation.factory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.model.Restaurant
import com.luisfagundes.itinerary.presentation.components.AccommodationCard
import com.luisfagundes.itinerary.presentation.components.ActivityCard
import com.luisfagundes.itinerary.presentation.components.FlightCard
import com.luisfagundes.itinerary.presentation.components.RestaurantCard

@Composable
internal fun ItineraryItemCardFactory(
    item: ItineraryItem,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
    ) {
        when (item) {
            is Accommodation -> AccommodationCard(
                accommodation = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default)
            )
            is Flight -> FlightCard(
                flight = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default)
            )
            is Restaurant -> RestaurantCard(
                restaurant = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default)
            )
            is Activity -> ActivityCard(
                activity = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default)
            )
        }
    }
}