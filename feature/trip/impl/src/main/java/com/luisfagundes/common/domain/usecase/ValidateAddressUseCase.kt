package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.CommonFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import javax.inject.Inject

internal class ValidateAddressUseCase @Inject constructor() {
    operator fun invoke(address: String?): FieldValidationResult {
        return when {
            address.isNullOrBlank() -> Invalid(CommonFieldError.EMPTY)
            else -> Valid
        }
    }
}
