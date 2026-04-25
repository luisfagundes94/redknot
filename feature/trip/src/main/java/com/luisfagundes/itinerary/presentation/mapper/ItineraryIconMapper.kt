package com.luisfagundes.itinerary.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Restaurant
import com.luisfagundes.itinerary.domain.model.ItineraryItemType

internal fun ItineraryItemType.toIcon() = when (this) {
    ItineraryItemType.ACCOMMODATION -> Icons.Default.Hotel
    ItineraryItemType.RESTAURANT -> Icons.Default.Restaurant
    ItineraryItemType.ACTIVITY -> Icons.Default.LocalActivity
    ItineraryItemType.FLIGHT -> Icons.Default.Flight
}