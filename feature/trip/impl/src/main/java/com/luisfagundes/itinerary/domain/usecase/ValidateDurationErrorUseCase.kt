package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.DurationFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

internal class ValidateDurationErrorUseCase @Inject constructor() {
    operator fun invoke(durationHours: Int, durationMinutes: Int): FieldValidationResult {
        val total = durationHours.hours + durationMinutes.minutes
        return when {
            total <= Duration.ZERO -> Invalid(DurationFieldError.INVALID_DURATION)
            else -> Valid
        }
    }
}
