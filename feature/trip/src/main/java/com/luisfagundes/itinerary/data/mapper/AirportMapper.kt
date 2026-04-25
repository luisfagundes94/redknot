package com.luisfagundes.itinerary.data.mapper

import com.luisfagundes.itinerary.data.model.AirportEntity
import com.luisfagundes.itinerary.domain.model.Airport

internal class AirportMapper {
    fun toDomain(entity: AirportEntity): Airport {
        return Airport(
            name = entity.name,
            city = entity.city
        )
    }

    fun toEntity(domain: Airport): AirportEntity {
        return AirportEntity(
            name = domain.name,
            city = domain.city
        )
    }
}