package com.luisfagundes.itinerary.data.mapper

import com.luisfagundes.itinerary.data.model.AccommodationEntity
import com.luisfagundes.itinerary.data.model.ActivityEntity
import com.luisfagundes.itinerary.data.model.FlightEntity
import com.luisfagundes.itinerary.data.model.RestaurantEntity
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal class ItineraryItemMapper(
    private val airportMapper: AirportMapper
) {
    fun toDomain(entity: FlightEntity): ItineraryItem.Flight =
        ItineraryItem.Flight(
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

    fun toDomain(entity: AccommodationEntity): ItineraryItem.Accommodation =
        ItineraryItem.Accommodation(
            id = entity.id,
            tripId = entity.tripId,
            date = entity.date,
            time = entity.time,
            name = entity.name,
            address = entity.address,
            checkInType = CheckInType.valueOf(entity.checkInType),
            imageUrl = entity.imageUrl
        )

    fun toDomain(entity: RestaurantEntity): ItineraryItem.Restaurant =
        ItineraryItem.Restaurant(
            id = entity.id,
            tripId = entity.tripId,
            date = entity.date,
            time = entity.time,
            name = entity.name
        )

    fun toDomain(entity: ActivityEntity): ItineraryItem.Activity =
        ItineraryItem.Activity(
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
        is ItineraryItem.Flight -> toEntity(domain)
        is ItineraryItem.Accommodation -> toEntity(domain)
        is ItineraryItem.Restaurant -> toEntity(domain)
        is ItineraryItem.Activity -> toEntity(domain)
    }

    fun toEntity(domain: ItineraryItem.Flight): FlightEntity =
        FlightEntity(
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

    fun toEntity(domain: ItineraryItem.Accommodation): AccommodationEntity =
        AccommodationEntity(
            id = domain.id,
            tripId = domain.tripId,
            date = domain.date,
            time = domain.time,
            name = domain.name,
            address = domain.address,
            checkInType = domain.checkInType.name,
            imageUrl = domain.imageUrl
        )

    fun toEntity(domain: ItineraryItem.Restaurant): RestaurantEntity =
        RestaurantEntity(
            id = domain.id,
            tripId = domain.tripId,
            date = domain.date,
            time = domain.time,
            name = domain.name
        )

    fun toEntity(domain: ItineraryItem.Activity): ActivityEntity =
        ActivityEntity(
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