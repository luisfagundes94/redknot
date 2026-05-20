package com.luisfagundes.itinerary.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal data class ItineraryItemTypePickerRoute(val tripId: Int) : NavKey

@Serializable
internal data class ActivityFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey

@Serializable
internal data class AccommodationFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey

@Serializable
internal data class FlightFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey

@Serializable
internal data class RestaurantFormRoute(
    val tripId: Int,
    val itineraryItemId: String? = null,
) : NavKey
