@file:Suppress("detekt:all")
package com.luisfagundes.redknot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.core.common.presentation.navigation.NavigationState
import com.luisfagundes.core.common.presentation.navigation.Navigator
import com.luisfagundes.core.common.presentation.navigation.toEntries
import com.luisfagundes.itinerary.presentation.navigation.itineraryEntry
import com.luisfagundes.trip.presentation.navigation.tripEntry

@Composable
fun AppNavDisplay(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        tripEntry(navigator)
        itineraryEntry(navigator)
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = navigator::goBack,
    )
}