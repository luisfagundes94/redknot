package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateStartDateUseCase @Inject constructor() {
    operator fun invoke(startDate: LocalDate?, endDate: LocalDate?): ValidationResult {
        if (startDate == null) {
            return ValidationResult(
                isValid = false,
                error = ValidationError.MISSING_START_DATE
            )
        }
        if (endDate != null && startDate > endDate) {
            return ValidationResult(
                isValid = false,
                error = ValidationError.INVALID_DATE_RANGE
            )
        }
        return ValidationResult(
            isValid = true,
            error = null
        )
    }
}