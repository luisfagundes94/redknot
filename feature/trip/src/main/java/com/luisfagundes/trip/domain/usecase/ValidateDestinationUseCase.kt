package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import javax.inject.Inject

internal class ValidateDestinationUseCase @Inject constructor() {
    operator fun invoke(destination: String): ValidationResult {
        if (destination.isBlank()) {
            return ValidationResult(
                isValid = false,
                error = ValidationError.EMPTY_DESTINATION
            )
        }
        if (destination.any { it.isDigit() }) {
            return ValidationResult(
                isValid = false,
                error = ValidationError.INVALID_DESTINATION_FORMAT
            )
        }
        return ValidationResult(
            isValid = true,
            error = null
        )
    }
}