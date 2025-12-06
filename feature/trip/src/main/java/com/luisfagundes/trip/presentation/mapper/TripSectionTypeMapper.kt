package com.luisfagundes.trip.presentation.mapper

import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.TripSectionType

internal fun TripSectionType.toTitleResId(): Int {
    return when (this) {
        TripSectionType.UPCOMING -> R.string.upcoming
        TripSectionType.PAST -> R.string.past
    }
}