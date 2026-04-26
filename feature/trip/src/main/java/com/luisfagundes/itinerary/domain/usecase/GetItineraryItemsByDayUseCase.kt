package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import java.time.LocalDate
import javax.inject.Inject

internal class GetItineraryItemsByDayUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(tripId: Int): Result<Map<LocalDate, List<ItineraryItem>>> {
        return repository.getItineraryItemList(tripId).map { items ->
            items.sortedWith(compareBy({ it.date }, { it.time }))
                .groupBy { it.date }
                .ifEmpty { emptyMap() }
        }
    }
}
