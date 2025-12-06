package com.luisfagundes.trip.presentation.mapper

import android.content.Context
import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationError.EMPTY_TITLE
import com.luisfagundes.trip.domain.model.ValidationError.MISSING_END_DATE
import com.luisfagundes.trip.domain.model.ValidationError.INVALID_DATE_RANGE
import com.luisfagundes.trip.domain.model.ValidationError.MISSING_START_DATE
import com.luisfagundes.trip.domain.model.ValidationError.EMPTY_DESTINATION
import com.luisfagundes.trip.domain.model.ValidationError.INVALID_DESTINATION_FORMAT

internal fun ValidationError.toErrorMessage(context: Context): String {
    val stringResId = when (this) {
        EMPTY_TITLE -> R.string.empty_title_error_message
        EMPTY_DESTINATION -> R.string.empty_destination_error_message
        INVALID_DESTINATION_FORMAT -> R.string.invalid_destination_error_message
        MISSING_START_DATE -> R.string.missing_start_date_error_message
        MISSING_END_DATE -> R.string.missing_end_date_error_message
        INVALID_DATE_RANGE -> R.string.invalid_date_range_error_message
    }
    return context.getString(stringResId)
}