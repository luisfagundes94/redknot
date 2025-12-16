package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.AirportEntity
import com.luisfagundes.trip.domain.model.Airport

internal class AirportMapper {
    fun mapToDomain(source: AirportEntity): Airport {
        return Airport(
            code = source.code,
            city = source.city
        )
    }

    fun mapToEntity(source: Airport): AirportEntity {
        return AirportEntity(
            code = source.code,
            city = source.city
        )
    }
}