package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.trip.domain.usecase.GetTripStartDateUseCase
import java.time.LocalDate
import javax.inject.Inject

internal class ItineraryItemFormUseCase @Inject constructor(
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    private val updateItineraryItemUseCase: UpdateItineraryItemUseCase,
    private val deleteItineraryItemUseCase: DeleteItineraryItemUseCase,
    private val getItineraryItemByIdUseCase: GetItineraryItemByIdUseCase,
    private val getTripStartDateUseCase: GetTripStartDateUseCase,
) {
    suspend fun getTripStartDate(tripId: Int): LocalDate? =
        getTripStartDateUseCase(tripId)

    suspend fun getItemById(itemId: String, type: ItineraryItemType): Result<ItineraryItem?> =
        getItineraryItemByIdUseCase(itemId, type)

    suspend fun submitItem(item: ItineraryItem, isEditing: Boolean): Result<Unit> =
        if (isEditing) updateItineraryItemUseCase(item) else createItineraryItemUseCase(item)

    suspend fun deleteItem(itemId: String, type: ItineraryItemType): Result<Unit> =
        deleteItineraryItemUseCase(itemId, type)
}
