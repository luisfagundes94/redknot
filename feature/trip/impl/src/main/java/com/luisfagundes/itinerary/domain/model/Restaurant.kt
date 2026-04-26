package com.luisfagundes.itinerary.domain.model

import java.time.LocalDate
import java.time.LocalTime

internal data class Restaurant(
    override val id: String,
    override val tripId: Int,
    override val date: LocalDate,
    override val time: LocalTime,
    val name: String,
    val address: String,
    val mealType: MealType
) : ItineraryItem