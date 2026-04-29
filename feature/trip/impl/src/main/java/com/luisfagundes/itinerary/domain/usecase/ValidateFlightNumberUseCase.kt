package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.model.FieldValidationResult.Invalid
import com.luisfagundes.common.domain.model.FieldValidationResult.Valid
import javax.inject.Inject

internal class ValidateFlightNumberUseCase @Inject constructor() {

    private companion object {
        // IATA: 2-char alphanumeric airline code + 1–4 digit flight number
        val REGEX = Regex("^[A-Za-z0-9]{2}\\d{1,4}$")
    }

    operator fun invoke(flightNumber: String): FieldValidationResult {
        val trimmed = flightNumber.trim()

        return when {
            trimmed.isBlank() -> Invalid(FieldValidationError.EMPTY)
            !REGEX.matches(trimmed) -> Invalid(FieldValidationError.INVALID_FLIGHT_NUMBER)
            else -> Valid
        }
    }
}
