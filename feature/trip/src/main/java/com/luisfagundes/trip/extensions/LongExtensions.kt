package com.luisfagundes.trip.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_PATTERN = "dd/MM/yyyy"
private const val TIMEZONE = "UTC"

fun Long?.convertMillisToDate(default: String = ""): String {
    if (this == null) return default
    val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone(TIMEZONE)
    }
    return formatter.format(Date(this))
}