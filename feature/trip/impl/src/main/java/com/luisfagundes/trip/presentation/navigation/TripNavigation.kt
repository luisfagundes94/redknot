package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.core.common.presentation.navigation.Navigator
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.Restaurant
import com.luisfagundes.trip.api.presentation.navigation.AccommodationFormRoute
import com.luisfagundes.trip.api.presentation.navigation.ActivityFormRoute
import com.luisfagundes.trip.api.presentation.navigation.DocumentsRoute
import com.luisfagundes.trip.api.presentation.navigation.FlightFormRoute
import com.luisfagundes.trip.api.presentation.navigation.ItineraryItemTypePickerRoute
import com.luisfagundes.trip.api.presentation.navigation.RestaurantFormRoute
import com.luisfagundes.trip.api.presentation.navigation.TripCreationRoute
import com.luisfagundes.trip.api.presentation.navigation.TripDetailsRoute
import com.luisfagundes.trip.api.presentation.navigation.TripListRoute
import com.luisfagundes.trip.presentation.screen.TripDetailsScreen
import com.luisfagundes.trip.presentation.screen.TripFormScreen
import com.luisfagundes.trip.presentation.screen.TripListScreen

fun EntryProviderScope<NavKey>.tripEntry(
    navigator: Navigator
) {
    entry<TripListRoute> {
        TripListScreen(
            onNavigateToTripForm = { navigator.navigateTo(TripCreationRoute) },
            onNavigateToTripDetails = { navigator.navigateTo(TripDetailsRoute(it)) }
        )
    }
    entry<TripCreationRoute> {
        TripFormScreen(
            onBackClick = navigator::goBack
        )
    }
    entry<TripDetailsRoute> { key ->
        val tripId = key.tripId
        TripDetailsScreen(
            tripId = tripId,
            onNavigateToAddItineraryItem = { navigator.navigateTo(ItineraryItemTypePickerRoute(tripId)) },
            onNavigateToEditItineraryItem = { item ->
                val route = when (item) {
                    is Flight -> FlightFormRoute(tripId, item.id)
                    is Accommodation -> AccommodationFormRoute(tripId, item.id)
                    is Restaurant -> RestaurantFormRoute(tripId, item.id)
                    is Activity -> ActivityFormRoute(tripId, item.id)
                }
                navigator.navigateTo(route)
            },
            onNavigateToDocumentForm = { navigator.navigateTo(DocumentsRoute) },
            onNavigateBack = navigator::goBack
        )
    }
}
