package com.luisfagundes.trip.presentation.tools

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatTripPeriod(startDate: LocalDate, endDate: LocalDate): String {
    val dayFormatter = DateTimeFormatter.ofPattern("MMM d")
    val fullFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

    return if (startDate.year == endDate.year) {
        "${startDate.format(dayFormatter)} - ${endDate.format(fullFormatter)}"
    } else {
        "${startDate.format(fullFormatter)} - ${endDate.format(fullFormatter)}"
    }
}