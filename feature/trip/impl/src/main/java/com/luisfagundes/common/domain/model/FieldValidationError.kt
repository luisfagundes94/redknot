package com.luisfagundes.common.domain.model

internal enum class FieldValidationError {
    EMPTY,
    CONTAINS_NUMBER,
    DATE_IN_THE_PAST,
    INVALID_FLIGHT_NUMBER,
    INVALID_DURATION
}
