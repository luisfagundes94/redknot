package com.luisfagundes.trip.tools.extensions

import com.luisfagundes.trip.tools.constants.DEFAULT_TIMEZONE
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Long?.convertMillisToLocalDate(): LocalDate? {
    if (this == null) return null
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.of(DEFAULT_TIMEZONE))
        .toLocalDate()
}

fun Long.convertMillisToLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.of(DEFAULT_TIMEZONE))
        .toLocalDate()
}