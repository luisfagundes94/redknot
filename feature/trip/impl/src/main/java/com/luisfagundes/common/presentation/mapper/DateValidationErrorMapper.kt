package com.luisfagundes.common.presentation.mapper

import android.content.Context
import com.luisfagundes.common.domain.model.DateValidationError
import com.luisfagundes.trip.R

internal fun DateValidationError.toErrorMessage(context: Context): String {
    val resId = when (this) {
        DateValidationError.DATE_IN_THE_PAST -> R.string.date_in_the_past_error_message
        DateValidationError.MISSING_DATE -> R.string.missing_date_error_message
    }
    return context.getString(resId)
}