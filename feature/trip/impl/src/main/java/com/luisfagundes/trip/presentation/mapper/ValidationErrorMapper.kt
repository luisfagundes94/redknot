package com.luisfagundes.trip.presentation.mapper

import android.content.Context
import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationError.EMPTY_TITLE
import com.luisfagundes.trip.domain.model.ValidationError.DATE_BEFORE_TODAY
import com.luisfagundes.trip.domain.model.ValidationError.MISSING_DATE
import com.luisfagundes.trip.domain.model.ValidationError.EMPTY_DESTINATION
import com.luisfagundes.trip.domain.model.ValidationError.INVALID_DESTINATION_FORMAT

internal fun ValidationError.toErrorMessage(context: Context): String {
    val stringResId = when (this) {
        EMPTY_TITLE -> R.string.empty_title_error_message
        EMPTY_DESTINATION -> R.string.empty_destination_error_message
        INVALID_DESTINATION_FORMAT -> R.string.invalid_destination_error_message
        MISSING_DATE -> R.string.missing_date_error_message
        DATE_BEFORE_TODAY -> R.string.date_before_today_error_message
    }
    return context.getString(stringResId)
}