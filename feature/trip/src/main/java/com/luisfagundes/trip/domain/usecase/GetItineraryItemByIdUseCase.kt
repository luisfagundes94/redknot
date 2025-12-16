package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ItineraryItem
import com.luisfagundes.trip.domain.repository.TripItineraryRepository
import javax.inject.Inject

internal class GetItineraryItemByIdUseCase @Inject constructor(
    private val repository: TripItineraryRepository
) {
    suspend operator fun invoke(itemId: String): Result<ItineraryItem> {
        return repository.getItineraryItemById(itemId)
    }
}
