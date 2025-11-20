package com.luisfagundes.trip.domain.model

internal data class Trip(
    val id: Int,
    val period: String,
    val imageUrl: String,
    val title: String,
    val location: String,
    val done: Boolean
)
