package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import javax.inject.Inject

internal class ValidateAddressUseCase @Inject constructor() {
    operator fun invoke(address: String?): FieldValidationResult {
        return when {
            address.isNullOrBlank() -> Invalid(FieldValidationError.EMPTY)
            else -> FieldValidationResult.Valid
        }
    }
}
