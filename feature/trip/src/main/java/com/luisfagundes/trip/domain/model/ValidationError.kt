package com.luisfagundes.trip.domain.model

internal enum class ValidationError {
    EMPTY_TITLE,
    EMPTY_DESTINATION,
    INVALID_DESTINATION_FORMAT,
    MISSING_START_DATE,
    MISSING_END_DATE,
    INVALID_DATE_RANGE
}
