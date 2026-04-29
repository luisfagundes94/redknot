package com.luisfagundes.itinerary.presentation.mapper

import android.content.Context
import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import com.luisfagundes.trip.R

internal fun ItineraryValidationError.toErrorMessage(context: Context): String {
    val resId = when (this) {
        ItineraryValidationError.EMPTY_TITLE -> R.string.empty_field_error_message
        ItineraryValidationError.EMPTY_NAME -> R.string.empty_name_error_message
        ItineraryValidationError.EMPTY_ADDRESS -> R.string.empty_address_error_message
        ItineraryValidationError.EMPTY_FLIGHT_NUMBER -> R.string.empty_flight_number_error_message
        ItineraryValidationError.INVALID_DURATION -> R.string.invalid_duration_error_message
        ItineraryValidationError.MISSING_DATE -> R.string.missing_date_error_message
        ItineraryValidationError.MISSING_TIME -> R.string.missing_time_error_message
    }
    return context.getString(resId)
}
