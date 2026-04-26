package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import javax.inject.Inject

internal class ValidateTitleUseCase @Inject constructor() {
    operator fun invoke(title: String): ValidationResult {
        if (title.isBlank()) {
            return ValidationResult.Invalid(ValidationError.EMPTY_TITLE)
        }
        return ValidationResult.Valid
    }
}