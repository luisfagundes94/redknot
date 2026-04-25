package com.luisfagundes.itinerary.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Restaurant
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.itinerary.domain.model.Restaurant

internal fun ItineraryItem.toIcon() = when (this) {
    is Accommodation -> Icons.Default.Hotel
    is Restaurant -> Icons.Default.Restaurant
    is Activity -> Icons.Default.LocalActivity
    is Flight -> Icons.Default.FlightTakeoff
}