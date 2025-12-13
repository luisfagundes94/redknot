package com.luisfagundes.trip.presentation.mapper

import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.TripStatus

internal fun TripStatus.toStringResId() = when (this) {
    TripStatus.ONGOING -> R.string.ongoing
    TripStatus.UPCOMING -> R.string.upcoming
    TripStatus.PAST -> R.string.past
}