package com.luisfagundes.trip.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.trip.presentation.screen.TripListScreen
import kotlinx.serialization.Serializable

@Serializable
data object TripListRoute : NavKey

@Composable
fun TripListScreen() {
    TripListScreen()
}