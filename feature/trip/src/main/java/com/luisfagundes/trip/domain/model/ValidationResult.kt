package com.luisfagundes.trip.domain.model

internal data class ValidationResult(
    val isValid: Boolean,
    val error: ValidationError?
)