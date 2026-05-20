package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.documents.presentation.navigation.AddDocumentFormRoute
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.Restaurant
import com.luisfagundes.itinerary.presentation.navigation.AccommodationFormRoute
import com.luisfagundes.itinerary.presentation.navigation.ActivityFormRoute
import com.luisfagundes.itinerary.presentation.navigation.FlightFormRoute
import com.luisfagundes.itinerary.presentation.navigation.ItineraryItemTypePickerRoute
import com.luisfagundes.itinerary.presentation.navigation.RestaurantFormRoute
import com.luisfagundes.trip.api.presentation.navigation.TripListRoute
import com.luisfagundes.trip.presentation.screen.TripDetailsScreen
import com.luisfagundes.trip.presentation.screen.TripFormScreen
import com.luisfagundes.trip.presentation.screen.TripListScreen

internal fun EntryProviderScope<NavKey>.tripEntries(
    navigateTo: (NavKey) -> Unit,
    goBack: () -> Unit,
) {
    entry<TripListRoute> {
        TripListScreen(
            onNavigateToTripForm = { navigateTo(TripCreationRoute) },
            onNavigateToTripDetails = { navigateTo(TripDetailsRoute(it)) }
        )
    }
    entry<TripCreationRoute> {
        TripFormScreen(
            onBackClick = { goBack() }
        )
    }
    entry<TripDetailsRoute> { key ->
        val tripId = key.tripId
        TripDetailsScreen(
            tripId = tripId,
            onNavigateToAddItineraryItem = { navigateTo(ItineraryItemTypePickerRoute(tripId)) },
            onNavigateToEditItineraryItem = { item ->
                val route = when (item) {
                    is Flight -> FlightFormRoute(tripId, item.id)
                    is Accommodation -> AccommodationFormRoute(tripId, item.id)
                    is Restaurant -> RestaurantFormRoute(tripId, item.id)
                    is Activity -> ActivityFormRoute(tripId, item.id)
                }
                navigateTo(route)
            },
            onNavigateToDocumentForm = { navigateTo(AddDocumentFormRoute(tripId)) },
            onNavigateBack = { goBack() }
        )
    }
}
