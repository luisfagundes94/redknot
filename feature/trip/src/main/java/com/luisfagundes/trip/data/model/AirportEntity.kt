package com.luisfagundes.trip.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class AirportEntity(
    val code: String,
    val city: String
)