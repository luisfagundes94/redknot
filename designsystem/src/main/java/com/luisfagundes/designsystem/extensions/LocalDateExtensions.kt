package com.luisfagundes.designsystem.extensions

import com.luisfagundes.designsystem.constants.DEFAULT_DATE_PATTERN
import com.luisfagundes.designsystem.constants.DEFAULT_TIMEZONE
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal fun LocalDate?.toFormattedString(
    fallback: String = "",
    pattern: String = DEFAULT_DATE_PATTERN
): String {
    if (this == null) return fallback
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

internal fun LocalDate.toTimestampMillis(): Long {
    return this.atStartOfDay()
        .atZone(ZoneId.of(DEFAULT_TIMEZONE))
        .toInstant()
        .toEpochMilli()
}