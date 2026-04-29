package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import javax.inject.Inject

internal class ValidateDestinationUseCase @Inject constructor() {
    operator fun invoke(destination: String): FieldValidationResult {
        return when {
            destination.isBlank() -> {
                FieldValidationResult.Invalid(FieldValidationError.EMPTY)
            }

            destination.any { it.isDigit() } -> {
                FieldValidationResult.Invalid(FieldValidationError.CONTAINS_NUMBER)
            }

            else -> FieldValidationResult.Valid
        }
    }
}