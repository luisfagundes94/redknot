package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal data object TripCreationRoute : NavKey

@Serializable
internal data class TripDetailsRoute(val tripId: Int) : NavKey
