package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.time.LocalDate
import java.time.LocalTime

internal data class ActivityFormUiState(
    val title: String = "",
    val titleError: FieldValidationError? = null,
    val description: String = "",
    val location: String = "",
    val date: LocalDate? = null,
    val dateError: FieldValidationError? = null,
    val time: LocalTime? = null,
    val timeError: FieldValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
    val isFormValid: Boolean
        get() {
            val hasNoErrors = listOf(titleError, dateError, timeError).all { it == null }
            val hasAllRequiredFields = title.isNotBlank() && date != null && time != null
            return hasNoErrors && hasAllRequiredFields
        }
}
