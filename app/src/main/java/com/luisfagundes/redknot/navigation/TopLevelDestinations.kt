package com.luisfagundes.redknot.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.itinerary.presentation.navigation.ItineraryRoute

enum class TopLevelDestinations(
    val label: String,
    val route: NavKey,
    val icon: ImageVector,
) {
    ITINERARY(
        label = "Itinerary",
        route = ItineraryRoute,
        icon = Icons.Default.Home
    )
}