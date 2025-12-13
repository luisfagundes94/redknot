package com.luisfagundes.trip.tools.extensions

import com.luisfagundes.trip.tools.constants.DEFAULT_DATE_PATTERN
import com.luisfagundes.trip.tools.constants.DEFAULT_TIMEZONE
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun formatTripPeriod(startDate: LocalDate, endDate: LocalDate): String {
    val dayFormatter = DateTimeFormatter.ofPattern("MMM d")
    val fullFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

    return if (startDate.year == endDate.year) {
        "${startDate.format(dayFormatter)} - ${endDate.format(fullFormatter)}"
    } else {
        "${startDate.format(fullFormatter)} - ${endDate.format(fullFormatter)}"
    }
}

fun getTripDurationInDays(startDate: LocalDate, endDate: LocalDate): Int {
    return ChronoUnit.DAYS.between(startDate, endDate).toInt()
}

fun LocalDate.toTimestampMillis(): Long {
    return this.atStartOfDay()
        .atZone(ZoneId.of(DEFAULT_TIMEZONE))
        .toInstant()
        .toEpochMilli()
}

fun LocalDate?.toFormattedString(
    fallback: String = "",
    pattern: String = DEFAULT_DATE_PATTERN
): String {
    if (this == null) return fallback
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}