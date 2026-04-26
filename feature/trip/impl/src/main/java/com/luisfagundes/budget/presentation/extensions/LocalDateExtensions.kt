package com.luisfagundes.budget.presentation.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate?.toFormattedString(pattern: String = "dd/MM/yyyy"): String =
    this?.format(DateTimeFormatter.ofPattern(pattern)).orEmpty()