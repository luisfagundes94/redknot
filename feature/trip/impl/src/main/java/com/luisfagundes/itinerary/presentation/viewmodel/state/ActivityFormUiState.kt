package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface ActivityFormUiState : UiState {
    data object Loading : ActivityFormUiState

    data class Content(
        val editingItemId: String? = null,
        val title: String = "",
        val titleError: FieldValidationError? = null,
        val description: String = "",
        val location: String = "",
        val date: LocalDate? = null,
        val dateError: FieldValidationError? = null,
        val time: LocalTime? = null,
        val timeError: FieldValidationError? = null,
        val isLoading: Boolean = false,
    ) : ActivityFormUiState {
        val isEditMode: Boolean get() = editingItemId != null

        private val hasAllRequiredFields: Boolean
            get() = title.isNotBlank() && date != null && time != null

        private val hasNoErrors: Boolean
            get() = titleError == null && dateError == null && timeError == null

        val isFormValid: Boolean get() = hasAllRequiredFields && hasNoErrors
    }
}
