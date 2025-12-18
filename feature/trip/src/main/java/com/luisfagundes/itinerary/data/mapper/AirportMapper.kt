package com.luisfagundes.itinerary.data.mapper

import com.luisfagundes.itinerary.data.model.AirportEntity
import com.luisfagundes.itinerary.domain.model.Airport

internal class AirportMapper {
    fun toDomain(entity: AirportEntity): Airport {
        return Airport(
            code = entity.code,
            city = entity.city
        )
    }

    fun toEntity(domain: Airport): AirportEntity {
        return AirportEntity(
            code = domain.code,
            city = domain.city,
        )
    }
}