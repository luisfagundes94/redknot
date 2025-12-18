package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class GetItineraryItemsUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(tripId: Int): Result<List<ItineraryItem>> {
        return repository.getItineraryItems(tripId)
    }
}
