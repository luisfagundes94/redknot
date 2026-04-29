package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.CommonFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import javax.inject.Inject

internal class ValidateDestinationUseCase @Inject constructor() {
    operator fun invoke(destination: String): FieldValidationResult {
        return when {
            destination.isBlank() -> Invalid(CommonFieldError.EMPTY)
            destination.any { it.isDigit() } -> Invalid(CommonFieldError.CONTAINS_NUMBER)
            else -> Valid
        }
    }
}