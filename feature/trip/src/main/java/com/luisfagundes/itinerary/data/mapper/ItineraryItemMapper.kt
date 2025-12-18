package com.luisfagundes.itinerary.data.mapper

import com.luisfagundes.itinerary.data.model.AccommodationEntity
import com.luisfagundes.itinerary.data.model.ActivityEntity
import com.luisfagundes.itinerary.data.model.FlightEntity
import com.luisfagundes.itinerary.data.model.ItineraryItemEntity
import com.luisfagundes.itinerary.data.model.RestaurantEntity
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.model.Restaurant

internal class ItineraryItemMapper(
    private val airportMapper: AirportMapper
) {
    fun toDomain(entity: ItineraryItemEntity): ItineraryItem = when (entity) {
        is FlightEntity -> toDomain(entity)
        is AccommodationEntity -> toDomain(entity)
        is RestaurantEntity -> toDomain(entity)
        is ActivityEntity -> toDomain(entity)
    }

    private fun toDomain(entity: FlightEntity) = Flight(
        id = entity.id,
        tripId = entity.tripId,
        date = entity.date,
        time = entity.time,
        flightNumber = entity.flightNumber,
        origin = airportMapper.toDomain(entity.origin),
        destination = airportMapper.toDomain(entity.destination),
        duration = entity.duration,
        seatNumber = entity.seatNumber
    )

    private fun toDomain(entity: AccommodationEntity) = Accommodation(
        id = entity.id,
        tripId = entity.tripId,
        date = entity.date,
        time = entity.time,
        name = entity.name,
        address = entity.address,
        checkInType = CheckInType.valueOf(entity.checkInType),
        imageUrl = entity.imageUrl
    )

    private fun toDomain(entity: RestaurantEntity) = Restaurant(
        id = entity.id,
        tripId = entity.tripId,
        date = entity.date,
        time = entity.time,
        name = entity.name
    )

    private fun toDomain(entity: ActivityEntity) = Activity(
        id = entity.id,
        tripId = entity.tripId,
        date = entity.date,
        time = entity.time,
        title = entity.title,
        description = entity.description,
        location = entity.location,
        imageUrl = entity.imageUrl
    )

    fun toEntity(domain: ItineraryItem): Any = when (domain) {
        is Flight -> toEntity(domain)
        is Accommodation -> toEntity(domain)
        is Restaurant -> toEntity(domain)
        is Activity -> toEntity(domain)
    }

    private fun toEntity(domain: Flight) = FlightEntity(
        id = domain.id,
        tripId = domain.tripId,
        date = domain.date,
        time = domain.time,
        flightNumber = domain.flightNumber,
        origin = airportMapper.toEntity(domain.origin),
        destination = airportMapper.toEntity(domain.destination),
        duration = domain.duration,
        seatNumber = domain.seatNumber
    )

    private fun toEntity(domain: Accommodation) = AccommodationEntity(
        id = domain.id,
        tripId = domain.tripId,
        date = domain.date,
        time = domain.time,
        name = domain.name,
        address = domain.address,
        checkInType = domain.checkInType.name,
        imageUrl = domain.imageUrl
    )

    private fun toEntity(domain: Restaurant) = RestaurantEntity(
        id = domain.id,
        tripId = domain.tripId,
        date = domain.date,
        time = domain.time,
        name = domain.name
    )

    private fun toEntity(domain: Activity) = ActivityEntity(
        id = domain.id,
        tripId = domain.tripId,
        date = domain.date,
        time = domain.time,
        title = domain.title,
        description = domain.description,
        location = domain.location,
        imageUrl = domain.imageUrl
    )
}