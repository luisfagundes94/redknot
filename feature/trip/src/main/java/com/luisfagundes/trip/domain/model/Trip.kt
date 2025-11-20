package com.luisfagundes.trip.domain.model

internal data class Trip(
    val period: String,
    val imageUrl: String,
    val title: String,
    val location: String
)
