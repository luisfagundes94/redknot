package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class DeleteItineraryItemUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(id: String, type: ItineraryItemType): Result<Unit> {
        return repository.deleteItineraryItem(id, type)
    }
}
