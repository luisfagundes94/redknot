package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate?): ValidationResult {
        if (date == null) {
            return ValidationResult.Invalid(ValidationError.MISSING_DATE)
        }
        if (date.isBefore(LocalDate.now())) {
            return ValidationResult.Invalid(ValidationError.DATE_BEFORE_TODAY)
        }
        return ValidationResult.Valid
    }
}