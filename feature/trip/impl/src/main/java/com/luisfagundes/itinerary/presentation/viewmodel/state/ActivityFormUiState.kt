package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import java.time.LocalDate
import java.time.LocalTime

internal data class ActivityFormUiState(
    val title: String = "",
    val titleError: ItineraryValidationError? = null,
    val description: String = "",
    val location: String = "",
    val date: LocalDate? = null,
    val dateError: ItineraryValidationError? = null,
    val time: LocalTime? = null,
    val timeError: ItineraryValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
    val isFormValid: Boolean
        get() {
            val hasNoErrors = listOf(titleError, dateError, timeError).all { it == null }
            val hasAllRequiredFields = title.isNotBlank() && date != null && time != null
            return hasNoErrors && hasAllRequiredFields
        }
}
