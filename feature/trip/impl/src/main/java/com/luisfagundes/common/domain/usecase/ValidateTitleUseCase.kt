package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import javax.inject.Inject

internal class ValidateTitleUseCase @Inject constructor() {
    operator fun invoke(title: String): FieldValidationResult {
        return when {
            title.isBlank() -> Invalid(FieldValidationError.EMPTY)
            else -> Valid
        }
    }
}
