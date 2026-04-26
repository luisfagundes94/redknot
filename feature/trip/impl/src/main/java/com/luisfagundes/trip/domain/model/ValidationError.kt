package com.luisfagundes.trip.domain.model

internal enum class ValidationError {
    EMPTY_TITLE,
    EMPTY_DESTINATION,
    INVALID_DESTINATION_FORMAT,
    MISSING_DATE,
    DATE_BEFORE_TODAY
}
