package com.luisfagundes.redknot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.itinerary.presentation.navigation.ItineraryRoute
import com.luisfagundes.itinerary.presentation.ui.ItineraryScreen

@Composable
fun AppNavigation(
    modifier: Modifier
) {
    val backStack = rememberNavBackStack(TopLevelDestinations.ITINERARY.route)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<ItineraryRoute> {
                ItineraryScreen()
            }
        }
    )
}