package com.luisfagundes.trip.presentation.state

import com.luisfagundes.trip.domain.model.Trip

internal data class TripCreationUiState(
    val trip: Trip? = null
)