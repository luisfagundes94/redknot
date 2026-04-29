package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.DateValidationError
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate?): DateValidationError? {
        return when {
            date == null -> DateValidationError.MISSING_DATE
            date.isBefore(LocalDate.now()) -> DateValidationError.DATE_IN_THE_PAST
            else -> null
        }
    }
}