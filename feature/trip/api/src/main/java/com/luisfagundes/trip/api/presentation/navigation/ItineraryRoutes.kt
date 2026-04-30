package com.luisfagundes.trip.api.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class ItineraryItemTypePickerRoute(val tripId: Int) : NavKey

@Serializable
data class ActivityFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey

@Serializable
data class AccommodationFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey

@Serializable
data class FlightFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey

@Serializable
data class RestaurantFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey