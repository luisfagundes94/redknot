package com.luisfagundes.redknot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.trip.presentation.navigation.TripListRoute
import com.luisfagundes.trip.presentation.navigation.TripListScreen

@Composable
fun RootAppNavigation(
    modifier: Modifier
) {
    val backStack = rememberNavBackStack(TopLevelDestinations.TRIP_LIST.route)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<TripListRoute> {
                TripListScreen()
            }
        }
    )
}