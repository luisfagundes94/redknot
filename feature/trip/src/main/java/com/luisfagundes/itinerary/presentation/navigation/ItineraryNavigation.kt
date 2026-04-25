package com.luisfagundes.itinerary.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.itinerary.presentation.screen.AccommodationFormScreen
import com.luisfagundes.itinerary.presentation.screen.ActivityFormScreen
import com.luisfagundes.itinerary.presentation.screen.FlightFormScreen
import com.luisfagundes.itinerary.presentation.screen.ItineraryItemTypePickerScreen
import com.luisfagundes.itinerary.presentation.screen.RestaurantFormScreen
import kotlinx.serialization.Serializable

@Serializable
data class ItineraryItemTypePickerRoute(val tripId: Int) : NavKey

@Serializable
data class ActivityFormRoute(val tripId: Int) : NavKey

@Serializable
data class AccommodationFormRoute(val tripId: Int) : NavKey

@Serializable
data class FlightFormRoute(val tripId: Int) : NavKey

@Serializable
data class RestaurantFormRoute(val tripId: Int) : NavKey

fun EntryProviderScope<NavKey>.itinerarySection(
    onActivityFormClick: (Int) -> Unit,
    onAccommodationFormClick: (Int) -> Unit,
    onFlightFormClick: (Int) -> Unit,
    onRestaurantFormClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    entry<ItineraryItemTypePickerRoute> { key ->
        ItineraryItemTypePickerScreen(
            onActivityClick = { onActivityFormClick(key.tripId) },
            onAccommodationClick = { onAccommodationFormClick(key.tripId) },
            onFlightClick = { onFlightFormClick(key.tripId) },
            onRestaurantClick = { onRestaurantFormClick(key.tripId) },
            onBackClick = onBackClick
        )
    }
    entry<ActivityFormRoute> { key ->
        ActivityFormScreen(tripId = key.tripId, onBackClick = onBackClick)
    }
    entry<AccommodationFormRoute> { key ->
        AccommodationFormScreen(tripId = key.tripId, onBackClick = onBackClick)
    }
    entry<FlightFormRoute> { key ->
        FlightFormScreen(tripId = key.tripId, onBackClick = onBackClick)
    }
    entry<RestaurantFormRoute> { key ->
        RestaurantFormScreen(tripId = key.tripId, onBackClick = onBackClick)
    }
}
