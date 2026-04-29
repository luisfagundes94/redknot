package com.luisfagundes.common.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate?): FieldValidationResult {
        return when {
            date == null -> Invalid(FieldValidationError.EMPTY)
            date.isBefore(LocalDate.now()) -> Invalid(FieldValidationError.DATE_IN_THE_PAST)
            else -> Valid
        }
    }
}