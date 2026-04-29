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
    private val hasAllRequiredFields: Boolean
        get() = name.isNotBlank() && address.isNotBlank() && date != null && time != null

    private val hasNoErrors: Boolean
        get() = nameError == null && addressError == null && dateError == null && timeError == null

    val isFormValid: Boolean
        get() = hasAllRequiredFields && hasNoErrors
}
