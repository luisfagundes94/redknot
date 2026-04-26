package com.luisfagundes.itinerary.data.dao

import com.luisfagundes.itinerary.domain.model.ItineraryItemType

internal interface ItineraryDaoFactory {
    fun <T> getDao(type: ItineraryItemType): BaseItineraryItemDao<T>
}