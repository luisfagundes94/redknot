package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.screen.TripCreationScreen
import com.luisfagundes.trip.presentation.screen.TripListScreen
import kotlinx.serialization.Serializable

@Serializable
data object TripListRoute : NavKey
@Serializable
data object TripCreationRoute : NavKey

fun EntryProviderScope<NavKey>.tripSection(
    onTripCreationClick: () -> Unit,
    onBackClick: () -> Unit
) {
    entry<TripListRoute> {
        TripListScreen(
            onTripClick = {},
            onTripCreationClick = onTripCreationClick
        )
    }
    entry<TripCreationRoute> {
        TripCreationScreen(
            onBackClick = onBackClick
        )
    }
}