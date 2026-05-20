package com.luisfagundes.itinerary.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.itinerary.presentation.screen.AccommodationFormScreen
import com.luisfagundes.itinerary.presentation.screen.ActivityFormScreen
import com.luisfagundes.itinerary.presentation.screen.FlightFormScreen
import com.luisfagundes.itinerary.presentation.screen.ItineraryItemTypePickerScreen
import com.luisfagundes.itinerary.presentation.screen.RestaurantFormScreen

private const val STEPS_TO_TRIP_DETAILS_FROM_PICKER = 2
private const val STEPS_TO_TRIP_DETAILS_FROM_EDIT = 1

private fun backStepsToTripDetails(itineraryItemId: String?) =
    if (itineraryItemId == null) {
        STEPS_TO_TRIP_DETAILS_FROM_PICKER
    } else {
        STEPS_TO_TRIP_DETAILS_FROM_EDIT
    }

internal fun EntryProviderScope<NavKey>.itineraryEntries(
    navigateTo: (NavKey) -> Unit,
    goBack: (Int) -> Unit,
) {
    entry<ItineraryItemTypePickerRoute> { key ->
        ItineraryItemTypePickerScreen(
            onActivityClick = { navigateTo(ActivityFormRoute(key.tripId)) },
            onAccommodationClick = { navigateTo(AccommodationFormRoute(key.tripId)) },
            onFlightClick = { navigateTo(FlightFormRoute(key.tripId)) },
            onRestaurantClick = { navigateTo(RestaurantFormRoute(key.tripId)) },
            onBackClick = { goBack(1) }
        )
    }
    entry<ActivityFormRoute> { key ->
        ActivityFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack(1) },
            onNavigateBackToTripDetails = { goBack(backStepsToTripDetails(key.itineraryItemId)) }
        )
    }
    entry<AccommodationFormRoute> { key ->
        AccommodationFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack(1) },
            onNavigateBackToTripDetails = { goBack(backStepsToTripDetails(key.itineraryItemId)) }
        )
    }
    entry<FlightFormRoute> { key ->
        FlightFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack(1) },
            onNavigateBackToTripDetails = { goBack(backStepsToTripDetails(key.itineraryItemId)) }
        )
    }
    entry<RestaurantFormRoute> { key ->
        RestaurantFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack(1) },
            onNavigateBackToTripDetails = { goBack(backStepsToTripDetails(key.itineraryItemId)) }
        )
    }
}
