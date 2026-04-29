package com.luisfagundes.common.presentation.mapper

import android.content.Context
import com.luisfagundes.common.domain.model.CommonFieldError
import com.luisfagundes.common.domain.model.DateFieldError
import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.DurationFieldError
import com.luisfagundes.common.domain.model.FlightFieldError
import com.luisfagundes.trip.R

internal fun FieldValidationError.toMessage(context: Context): String {
    val stringResId = when (this) {
        CommonFieldError.EMPTY -> R.string.empty_field_error_message
        CommonFieldError.CONTAINS_NUMBER -> R.string.contains_number_error_message
        DateFieldError.IN_THE_PAST -> R.string.date_in_the_past_error_message
        DateFieldError.MISSING -> R.string.missing_date_error_message
        FlightFieldError.INVALID_NUMBER -> R.string.invalid_flight_number_error_message
        DurationFieldError.INVALID_DURATION -> R.string.invalid_duration_error_message
    }
    return context.getString(stringResId)
}
