package com.luisfagundes.trip.presentation.state

import java.time.LocalDate

internal data class TripCreationUiState(
    val name: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val destination: String = "",
) {
    fun isFormValid(): Boolean = listOf(
        name.isNotBlank(),
        startDate != null,
        endDate != null,
        destination.isNotBlank()
    ).all { it }
}