package com.luisfagundes.designsystem.extensions

import com.luisfagundes.designsystem.constants.DEFAULT_TIMEZONE
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

internal fun Long?.convertMillisToLocalDate(): LocalDate? {
    if (this == null) return null
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.of(DEFAULT_TIMEZONE))
        .toLocalDate()
}