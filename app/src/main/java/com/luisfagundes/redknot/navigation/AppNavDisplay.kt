@file:Suppress("detekt:all")
package com.luisfagundes.redknot.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.core.common.presentation.navigation.NavigationState
import com.luisfagundes.core.common.presentation.navigation.Navigator
import com.luisfagundes.core.common.presentation.navigation.toEntries
import com.luisfagundes.documents.presentation.navigation.documentsEntry
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
        documentsEntry(navigator)
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = navigator::goBack,
        transitionSpec = { slideForward() },
        popTransitionSpec = { slideBackward() },
        predictivePopTransitionSpec = { slideBackward() }
    )
}

private fun slideForward(): ContentTransform =
    slideInHorizontally(initialOffsetX = { it }) togetherWith
        slideOutHorizontally(targetOffsetX = { -it })

private fun slideBackward(): ContentTransform =
    slideInHorizontally(initialOffsetX = { -it }) togetherWith
        slideOutHorizontally(targetOffsetX = { it })