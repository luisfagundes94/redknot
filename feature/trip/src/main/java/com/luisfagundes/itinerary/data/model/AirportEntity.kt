package com.luisfagundes.itinerary.data.model

import androidx.room.Entity

@Entity
internal data class AirportEntity(
    val name: String,
    val city: String
)