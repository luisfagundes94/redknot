package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate?): ValidationResult {
        return when {
            date == null -> {
                ValidationResult.Invalid(ValidationError.MISSING_DATE)
            }

            date.isBefore(LocalDate.now()) -> {
                ValidationResult.Invalid(ValidationError.DATE_BEFORE_TODAY)
            }

            else -> {
                ValidationResult.Valid
            }
        }
    }
}