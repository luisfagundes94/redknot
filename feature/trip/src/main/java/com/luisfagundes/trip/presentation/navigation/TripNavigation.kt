package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.trip.presentation.screen.TripFormScreen
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
            onCreateTripClick = onTripCreationClick
        )
    }
    entry<TripCreationRoute> {
        TripFormScreen(
            onBackClick = onBackClick
        )
    }
}