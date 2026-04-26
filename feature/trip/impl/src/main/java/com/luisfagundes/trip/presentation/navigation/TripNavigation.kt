package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.core.navigation.Navigator
import com.luisfagundes.trip.api.presentation.navigation.ItineraryItemTypePickerRoute
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
        val id = key.tripId

        TripDetailsScreen(
            tripId = id,
            onAddItineraryItemClick = { navigator.navigateTo(ItineraryItemTypePickerRoute(id)) },
            onBackClick = navigator::goBack
        )
    }
}