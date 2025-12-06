package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import javax.inject.Inject

internal class ValidateTitleUseCase @Inject constructor() {
    operator fun invoke(title: String): ValidationResult {
        if (title.isBlank()) {
            return ValidationResult(
                isValid = false,
                error = ValidationError.EMPTY_TITLE
            )
        }
        return ValidationResult(
            isValid = true,
            error = null
        )
    }
}