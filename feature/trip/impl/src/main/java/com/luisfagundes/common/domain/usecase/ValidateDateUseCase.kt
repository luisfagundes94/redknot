package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.DateFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate?): FieldValidationResult {
        return when {
            date == null -> Invalid(DateFieldError.MISSING)
            date.isBefore(LocalDate.now()) -> Invalid(DateFieldError.IN_THE_PAST)
            else -> Valid
        }
    }
}