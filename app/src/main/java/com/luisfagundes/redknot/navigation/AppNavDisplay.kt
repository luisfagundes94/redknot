package com.luisfagundes.redknot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.itinerary.presentation.navigation.AccommodationFormRoute
import com.luisfagundes.itinerary.presentation.navigation.ActivityFormRoute
import com.luisfagundes.itinerary.presentation.navigation.FlightFormRoute
import com.luisfagundes.itinerary.presentation.navigation.ItineraryItemTypePickerRoute
import com.luisfagundes.itinerary.presentation.navigation.RestaurantFormRoute
import com.luisfagundes.itinerary.presentation.navigation.itinerarySection
import com.luisfagundes.trip.presentation.navigation.TripCreationRoute
import com.luisfagundes.trip.presentation.navigation.TripDetailsRoute
import com.luisfagundes.trip.presentation.navigation.tripSection

@Composable
fun AppNavDisplay(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        tripSection(
            onCreateTripClick = { navigator.navigate(TripCreationRoute) },
            onTripClick = { tripId -> navigator.navigate(TripDetailsRoute(tripId)) },
            onNewItineraryItemClick = { tripId ->
                navigator.navigate(ItineraryItemTypePickerRoute(tripId))
            },
            onBackClick = { navigator.goBack() }
        )
        itinerarySection(
            onActivityFormClick = { tripId -> navigator.navigate(ActivityFormRoute(tripId)) },
            onAccommodationFormClick = { tripId ->
                navigator.navigate(AccommodationFormRoute(tripId))
            },
            onFlightFormClick = { tripId -> navigator.navigate(FlightFormRoute(tripId)) },
            onRestaurantFormClick = { tripId -> navigator.navigate(RestaurantFormRoute(tripId)) },
            onBackClick = { navigator.goBack() }
        )
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
    )
}