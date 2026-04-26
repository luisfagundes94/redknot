package com.luisfagundes.trip.api.presentation.navigation

import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.NavKey

@Serializable
data object TripListRoute : NavKey

@Serializable
data object TripCreationRoute : NavKey

@Serializable
data class TripDetailsRoute(val tripId: Int) : NavKey