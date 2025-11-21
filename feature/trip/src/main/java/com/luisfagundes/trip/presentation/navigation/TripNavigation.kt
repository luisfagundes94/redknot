package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.trip.presentation.screen.TripCreationScreen
import com.luisfagundes.trip.presentation.screen.TripListScreen
import kotlinx.serialization.Serializable

@Serializable
data object TripListRoute : NavKey
@Serializable
data object TripCreationRoute : NavKey

fun EntryProviderScope<NavKey>.tripSection(backStack: NavBackStack<NavKey>) {
    entry<TripListRoute> {
        TripListScreen(
            onTripClick = {},
            onTripCreationClick = { backStack.add(TripCreationRoute) }
        )
    }
    entry<TripCreationRoute> {
        TripCreationScreen(
            onBackClick = { backStack.removeLastOrNull() }
        )
    }
}