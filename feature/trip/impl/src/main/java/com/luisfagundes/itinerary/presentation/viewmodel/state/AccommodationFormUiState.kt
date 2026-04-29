package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.itinerary.domain.model.CheckInType
import java.time.LocalDate
import java.time.LocalTime

internal data class AccommodationFormUiState(
    val name: String = "",
    val nameError: FieldValidationError? = null,
    val address: String = "",
    val addressError: FieldValidationError? = null,
    val checkInType: CheckInType = CheckInType.CHECK_IN,
    val date: LocalDate? = null,
    val dateError: FieldValidationError? = null,
    val time: LocalTime? = null,
    val timeError: FieldValidationError? = null,
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
