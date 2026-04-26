package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.core.presentation.arch.state.UiState
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import java.time.LocalDate
import java.time.LocalTime

internal data class AccommodationFormUiState(
    val name: String = "",
    val nameError: ItineraryValidationError? = null,
    val address: String = "",
    val addressError: ItineraryValidationError? = null,
    val checkInType: CheckInType = CheckInType.CHECK_IN,
    val date: LocalDate? = null,
    val dateError: ItineraryValidationError? = null,
    val time: LocalTime? = null,
    val timeError: ItineraryValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
    val isFormValid: Boolean
        get() {
            val hasNoErrors = listOf(nameError, addressError, dateError, timeError).all { it == null }
            val hasAllRequiredFields = name.isNotBlank() && address.isNotBlank() &&
                    date != null && time != null
            return hasNoErrors && hasAllRequiredFields
        }
}
