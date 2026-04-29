package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.itinerary.domain.model.MealType
import java.time.LocalDate
import java.time.LocalTime

internal data class RestaurantFormUiState(
    val name: String = "",
    val address: String = "",
    val mealType: MealType = MealType.BREAKFAST,
    val nameError: FieldValidationError? = null,
    val date: LocalDate? = null,
    val dateError: FieldValidationError? = null,
    val time: LocalTime? = null,
    val timeError: FieldValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
    val isFormValid: Boolean
        get() {
            val hasNoErrors = listOf(nameError, dateError, timeError).all { it == null }
            val hasAllRequiredFields = name.isNotBlank() && date != null && time != null
            return hasNoErrors && hasAllRequiredFields
        }
}
