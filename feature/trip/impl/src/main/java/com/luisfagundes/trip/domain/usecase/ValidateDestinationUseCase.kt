package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import javax.inject.Inject

internal class ValidateDestinationUseCase @Inject constructor() {
    operator fun invoke(destination: String): ValidationResult {
        return when {
            destination.isBlank() -> {
                ValidationResult.Invalid(ValidationError.EMPTY_DESTINATION)
            }

            destination.any { it.isDigit() } -> {
                ValidationResult.Invalid(ValidationError.INVALID_DESTINATION_FORMAT)
            }

            else -> ValidationResult.Valid
        }
    }
}