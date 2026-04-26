package com.luisfagundes.itinerary.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.core.navigation.Navigator
import com.luisfagundes.itinerary.presentation.screen.AccommodationFormScreen
import com.luisfagundes.itinerary.presentation.screen.ActivityFormScreen
import com.luisfagundes.itinerary.presentation.screen.FlightFormScreen
import com.luisfagundes.itinerary.presentation.screen.ItineraryItemTypePickerScreen
import com.luisfagundes.itinerary.presentation.screen.RestaurantFormScreen
import com.luisfagundes.trip.api.presentation.navigation.AccommodationFormRoute
import com.luisfagundes.trip.api.presentation.navigation.ActivityFormRoute
import com.luisfagundes.trip.api.presentation.navigation.FlightFormRoute
import com.luisfagundes.trip.api.presentation.navigation.ItineraryItemTypePickerRoute
import com.luisfagundes.trip.api.presentation.navigation.RestaurantFormRoute

private const val STEPS_TO_TRIP_DETAILS = 2

fun EntryProviderScope<NavKey>.itineraryEntry(
    navigator: Navigator
) {
    entry<ItineraryItemTypePickerRoute> { key ->
        ItineraryItemTypePickerScreen(
            onActivityClick = { navigator.navigateTo(ActivityFormRoute(key.tripId)) },
            onAccommodationClick = { navigator.navigateTo(AccommodationFormRoute(key.tripId)) },
            onFlightClick = { navigator.navigateTo(FlightFormRoute(key.tripId)) },
            onRestaurantClick = { navigator.navigateTo(RestaurantFormRoute(key.tripId)) },
            onBackClick = navigator::goBack
        )
    }
    entry<ActivityFormRoute> { key ->
        ActivityFormScreen(
            tripId = key.tripId,
            onBackClick = navigator::goBack,
            onNavigateBackToTripDetails = { navigator.goBack(STEPS_TO_TRIP_DETAILS) }
        )
    }
    entry<AccommodationFormRoute> { key ->
        AccommodationFormScreen(
            tripId = key.tripId,
            onBackClick = navigator::goBack,
            onNavigateBackToTripDetails = { navigator.goBack(STEPS_TO_TRIP_DETAILS) }
        )
    }
    entry<FlightFormRoute> { key ->
        FlightFormScreen(
            tripId = key.tripId,
            onBackClick = navigator::goBack,
            onNavigateBackToTripDetails = { navigator.goBack(STEPS_TO_TRIP_DETAILS) }
        )
    }
    entry<RestaurantFormRoute> { key ->
        RestaurantFormScreen(
            tripId = key.tripId,
            onBackClick = navigator::goBack,
            onNavigateBackToTripDetails = { navigator.goBack(STEPS_TO_TRIP_DETAILS) }
        )
    }
}
