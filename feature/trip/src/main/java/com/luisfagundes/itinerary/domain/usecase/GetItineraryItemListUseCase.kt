package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class GetItineraryItemListUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(tripId: Int): Result<List<ItineraryItem>> {
        return repository.getItineraryItemList(tripId).map { items ->
            items.sortedWith(compareBy({ it.date }, { it.time }))
        }
    }
}
