package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.DateFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import java.time.LocalTime
import javax.inject.Inject

internal class ValidateTimeUseCase @Inject constructor() {
    operator fun invoke(time: LocalTime?): FieldValidationResult {
        return when (time) {
            null -> Invalid(DateFieldError.MISSING)
            else -> Valid
        }
    }
}
