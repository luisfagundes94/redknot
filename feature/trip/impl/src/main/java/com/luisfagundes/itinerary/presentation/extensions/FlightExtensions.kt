package com.luisfagundes.itinerary.presentation.extensions

import com.luisfagundes.itinerary.domain.model.Flight
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.toJavaDuration

internal fun Flight.getTimeRange(): String {
    val javaDuration = duration.toJavaDuration()
    val arrivalTime = time.plus(javaDuration)

    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
    return "${time.format(formatter)} - ${arrivalTime.format(formatter)}"
}