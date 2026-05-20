package com.luisfagundes.itinerary.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.itinerary.presentation.screen.AccommodationFormScreen
import com.luisfagundes.itinerary.presentation.screen.ActivityFormScreen
import com.luisfagundes.itinerary.presentation.screen.FlightFormScreen
import com.luisfagundes.itinerary.presentation.screen.ItineraryItemTypePickerScreen
import com.luisfagundes.itinerary.presentation.screen.RestaurantFormScreen
import com.luisfagundes.trip.presentation.navigation.TripDetailsRoute
import kotlin.reflect.KClass

internal fun EntryProviderScope<NavKey>.itineraryEntries(
    navigateTo: (NavKey) -> Unit,
    goBack: () -> Unit,
    popBackTo: (KClass<out NavKey>) -> Unit,
) {
    entry<ItineraryItemTypePickerRoute> { key ->
        ItineraryItemTypePickerScreen(
            onActivityClick = { navigateTo(ActivityFormRoute(key.tripId)) },
            onAccommodationClick = { navigateTo(AccommodationFormRoute(key.tripId)) },
            onFlightClick = { navigateTo(FlightFormRoute(key.tripId)) },
            onRestaurantClick = { navigateTo(RestaurantFormRoute(key.tripId)) },
            onBackClick = { goBack() }
        )
    }
    entry<ActivityFormRoute> { key ->
        ActivityFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack() },
            onNavigateBackToTripDetails = { popBackTo(TripDetailsRoute::class) }
        )
    }
    entry<AccommodationFormRoute> { key ->
        AccommodationFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack() },
            onNavigateBackToTripDetails = { popBackTo(TripDetailsRoute::class) }
        )
    }
    entry<FlightFormRoute> { key ->
        FlightFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack() },
            onNavigateBackToTripDetails = { popBackTo(TripDetailsRoute::class) }
        )
    }
    entry<RestaurantFormRoute> { key ->
        RestaurantFormScreen(
            tripId = key.tripId,
            itineraryItemId = key.itineraryItemId,
            onBackClick = { goBack() },
            onNavigateBackToTripDetails = { popBackTo(TripDetailsRoute::class) }
        )
    }
}
