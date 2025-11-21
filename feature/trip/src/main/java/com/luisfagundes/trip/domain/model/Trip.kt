package com.luisfagundes.trip.domain.model

import java.time.LocalDate

internal data class Trip(
    val id: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val imageUrl: String,
    val title: String,
    val location: String,
    val done: Boolean
)
