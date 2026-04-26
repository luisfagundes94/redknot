package com.luisfagundes.trip.tools.extensions

import java.time.LocalDate
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

fun java.time.LocalTime?.toFormattedString(fallback: String = ""): String {
    if (this == null) return fallback
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}