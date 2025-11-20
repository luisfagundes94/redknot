package com.luisfagundes.redknot.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.itinerary.presentation.navigation.Itinerary

enum class TopLevelDestinations(
    val label: String,
    val screen: NavKey,
    val icon: ImageVector,
) {
    ITINERARY(
        label = "Itinerary",
        screen = Itinerary,
        icon = Icons.Default.Home
    )
}