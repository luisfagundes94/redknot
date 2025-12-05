package com.luisfagundes.trip.extensions

import com.luisfagundes.trip.tools.constants.DEFAULT_TIMEZONE

fun Long?.convertMillisToLocalDate(): java.time.LocalDate? {
    if (this == null) return null
    return java.time.Instant.ofEpochMilli(this)
        .atZone(java.time.ZoneId.of(DEFAULT_TIMEZONE))
        .toLocalDate()
}