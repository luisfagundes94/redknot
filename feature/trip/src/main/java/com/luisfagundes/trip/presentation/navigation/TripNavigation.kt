package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.trip.presentation.screen.TripDetailsScreen
import com.luisfagundes.trip.presentation.screen.TripFormScreen
import com.luisfagundes.trip.presentation.screen.TripListScreen
import com.luisfagundes.trip.presentation.viewmodel.action.TripListUiAction
import kotlinx.serialization.Serializable

@Serializable
data object TripListRoute : NavKey

@Serializable
data object TripCreationRoute : NavKey

@Serializable
data class TripDetailsRoute(val tripId: Int) : NavKey

fun EntryProviderScope<NavKey>.tripSection(
    onTripCreationClick: () -> Unit,
    onTripClick: (tripId: Int) -> Unit,
    onNewItineraryItemClick: () -> Unit,
    onBackClick: () -> Unit
) {
    entry<TripListRoute> {
        TripListScreen(
            onAction = { action ->
                when (action) {
                    is TripListUiAction.NavigateToTripForm -> onTripCreationClick()
                    is TripListUiAction.NavigateToTripDetails -> onTripClick(action.id)
                }
            }
        )
    }
    entry<TripCreationRoute> {
        TripFormScreen(
            onBackClick = onBackClick
        )
    }
    entry<TripDetailsRoute> { key ->
        TripDetailsScreen(
            tripId = key.tripId,
            onNewItineraryItemClick = onNewItineraryItemClick,
            onBackClick = onBackClick
        )
    }
}