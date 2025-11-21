package com.luisfagundes.redknot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.trip.presentation.navigation.TripCreationRoute
import com.luisfagundes.trip.presentation.navigation.tripSection

@Composable
fun AppNavDisplay(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        tripSection(
            onTripCreationClick = { navigator.navigate(TripCreationRoute) },
            onBackClick = { navigator.goBack() }
        )
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
    )
}