package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ItineraryItem
import com.luisfagundes.trip.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class GetItineraryItemsUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(tripId: Int): Result<List<ItineraryItem>> {
        return repository.getItineraryItems(tripId)
    }
}
