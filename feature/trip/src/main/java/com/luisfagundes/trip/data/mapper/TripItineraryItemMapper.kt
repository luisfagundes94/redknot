package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.ItineraryItemEntity
import com.luisfagundes.trip.domain.model.CheckInType
import com.luisfagundes.trip.domain.model.ItineraryItem
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

internal class TripItineraryItemMapper @Inject constructor(
    private val airportMapper: AirportMapper
) {
    fun mapToDomain(source: ItineraryItemEntity): ItineraryItem {
        return when (source.type) {
            "flight" -> ItineraryItem.Flight(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                time = source.time,
                flightNumber = source.flightNumber.orEmpty(),
                origin = airportMapper.mapToDomain(requireNotNull(source.originAirport)),
                destination = airportMapper.mapToDomain(requireNotNull(source.destinationAirport)),
                duration = requireNotNull(source.durationMillis).milliseconds,
                seatNumber = source.seatNumber.orEmpty()
            )
            "accommodation" -> ItineraryItem.Accommodation(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                time = source.time,
                name = source.accommodationName.orEmpty(),
                address = source.address.orEmpty(),
                checkInType = CheckInType.valueOf(source.checkInType ?: "CHECK_IN"),
                imageUrl = source.accommodationImageUrl.orEmpty()
            )
            "restaurant" -> ItineraryItem.Restaurant(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                time = source.time,
                name = source.restaurantName.orEmpty()
            )
            "activity" -> ItineraryItem.Activity(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                time = source.time,
                title = source.activityTitle.orEmpty(),
                description = source.description,
                location = source.location,
                imageUrl = source.activityImageUrl
            )
            else -> throw IllegalArgumentException("Unknown itinerary item type: ${source.type}")
        }
    }

    fun mapToEntity(source: ItineraryItem): ItineraryItemEntity {
        return when (source) {
            is ItineraryItem.Flight -> ItineraryItemEntity(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                type = "flight",
                time = source.time,
                flightNumber = source.flightNumber,
                originAirport = airportMapper.mapToEntity(source.origin),
                destinationAirport = airportMapper.mapToEntity(source.destination),
                durationMillis = source.duration.inWholeMilliseconds,
                seatNumber = source.seatNumber
            )
            is ItineraryItem.Accommodation -> ItineraryItemEntity(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                type = "accommodation",
                time = source.time,
                accommodationName = source.name,
                address = source.address,
                checkInType = source.checkInType.name,
                accommodationImageUrl = source.imageUrl
            )
            is ItineraryItem.Restaurant -> ItineraryItemEntity(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                type = "restaurant",
                time = source.time,
                restaurantName = source.name
            )
            is ItineraryItem.Activity -> ItineraryItemEntity(
                id = source.id,
                tripId = source.tripId,
                date = source.date,
                type = "activity",
                time = source.time,
                activityTitle = source.title,
                description = source.description,
                location = source.location,
                activityImageUrl = source.imageUrl
            )
        }
    }
}