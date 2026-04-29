package com.luisfagundes.common.domain.model

internal enum class DateFieldError : FieldValidationError {
    IN_THE_PAST,
    MISSING,
}
