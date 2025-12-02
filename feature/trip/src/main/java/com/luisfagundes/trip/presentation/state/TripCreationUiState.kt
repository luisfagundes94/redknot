package com.luisfagundes.trip.presentation.state

internal data class TripCreationUiState(
    val name: String = "",
    val startDate: Long? = null,
    val endDate: Long? = null,
    val destination: String = "",
) {
    fun isFormValid(): Boolean = listOf(
        name.isNotBlank(),
        startDate != null,
        endDate != null,
        destination.isNotBlank()
    ).all { it }
}