package com.luisfagundes.common.domain.model

internal sealed class FieldValidationResult {
    data object Valid : FieldValidationResult()
    data class Invalid(val error: FieldValidationError) : FieldValidationResult()
}

internal fun FieldValidationResult.errorOrNull(): FieldValidationError? = when (this) {
    is FieldValidationResult.Valid -> null
    is FieldValidationResult.Invalid -> error
}