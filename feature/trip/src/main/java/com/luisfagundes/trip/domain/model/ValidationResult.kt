package com.luisfagundes.trip.domain.model

internal sealed class ValidationResult {
    data object Valid : ValidationResult()
    data class Invalid(val error: ValidationError) : ValidationResult()
}

internal fun ValidationResult.errorOrNull(): ValidationError? = when (this) {
    is ValidationResult.Valid -> null
    is ValidationResult.Invalid -> error
}