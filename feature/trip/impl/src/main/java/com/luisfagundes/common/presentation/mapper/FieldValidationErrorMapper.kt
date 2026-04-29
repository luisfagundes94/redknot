package com.luisfagundes.common.presentation.mapper

import android.content.Context
import com.luisfagundes.trip.R
import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationError.EMPTY
import com.luisfagundes.common.domain.model.FieldValidationError.DATE_IN_THE_PAST
import com.luisfagundes.common.domain.model.FieldValidationError.CONTAINS_NUMBER
import com.luisfagundes.common.domain.model.FieldValidationError.INVALID_FLIGHT_NUMBER
import com.luisfagundes.common.domain.model.FieldValidationError.INVALID_DURATION

internal fun FieldValidationError.toMessage(context: Context): String {
    val stringResId = when (this) {
        EMPTY -> R.string.empty_field_error_message
        CONTAINS_NUMBER -> R.string.contains_number_error_message
        DATE_IN_THE_PAST -> R.string.date_in_the_past_error_message
        INVALID_FLIGHT_NUMBER -> R.string.invalid_flight_number_error_message
        INVALID_DURATION -> R.string.invalid_duration_error_message
    }
    return context.getString(stringResId)
}