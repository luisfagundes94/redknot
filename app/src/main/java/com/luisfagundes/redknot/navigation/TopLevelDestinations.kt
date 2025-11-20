package com.luisfagundes.redknot.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.trip.presentation.navigation.TripListRoute

enum class TopLevelDestinations(
    val label: String,
    val route: NavKey,
    val icon: ImageVector,
) {
    TRIP_LIST(
        label = "My Trips",
        route = TripListRoute,
        icon = Icons.Default.TravelExplore
    )
}