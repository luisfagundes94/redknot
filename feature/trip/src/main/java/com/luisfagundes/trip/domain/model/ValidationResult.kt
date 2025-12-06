package com.luisfagundes.trip.domain.model

internal sealed class ValidationResult {
    data object Valid : ValidationResult()
    data class Invalid(val error: ValidationError) : ValidationResult()
}

internal fun ValidationResult.errorOrNull(): ValidationError? =
    (this as? ValidationResult.Invalid)?.error