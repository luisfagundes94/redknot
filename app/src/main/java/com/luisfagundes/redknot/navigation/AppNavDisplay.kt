@file:Suppress("detekt:all")
package com.luisfagundes.redknot.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.trip.presentation.navigation.tripFeatureEntries

@Composable
fun AppNavDisplay(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        tripFeatureEntries(
            navigateTo = backStack::navigateTo,
            goBack = backStack::goBack,
        )
    }

    val decorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
        rememberViewModelStoreNavEntryDecorator<NavKey>(),
    )

    NavDisplay(
        modifier = modifier,
        entries = rememberDecoratedNavEntries(backStack, decorators, entryProvider),
        onBack = { backStack.goBack() },
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
