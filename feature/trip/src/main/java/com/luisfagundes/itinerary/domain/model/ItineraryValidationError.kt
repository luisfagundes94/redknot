package com.luisfagundes.itinerary.domain.model

internal enum class ItineraryValidationError {
    EMPTY_TITLE,
    EMPTY_NAME,
    EMPTY_ADDRESS,
    EMPTY_FLIGHT_NUMBER,
    EMPTY_AIRPORT_CODE,
    INVALID_AIRPORT_CODE,
    INVALID_DURATION,
    MISSING_DATE,
    MISSING_TIME,
}
