package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateEndDateUseCase @Inject constructor() {
    operator fun invoke(startDate: LocalDate?, endDate: LocalDate?): ValidationResult {
        if (endDate == null) {
            return ValidationResult(
                isValid = false,
                error = ValidationError.MISSING_END_DATE
            )
        }
        if (startDate != null && startDate > endDate) {
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