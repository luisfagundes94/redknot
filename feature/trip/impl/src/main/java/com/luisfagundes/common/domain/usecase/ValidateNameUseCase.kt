package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import javax.inject.Inject

internal class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String?): FieldValidationResult {
        return when {
            name.isNullOrBlank() -> Invalid(FieldValidationError.EMPTY)
            else -> Valid
        }
    }
}
