package com.luisfagundes.itinerary.data.dao

import com.luisfagundes.itinerary.domain.model.ItineraryItemType

internal interface ItineraryItemDaoFactory {
    fun <T> getDao(type: ItineraryItemType): BaseItineraryItemDao<T>
}