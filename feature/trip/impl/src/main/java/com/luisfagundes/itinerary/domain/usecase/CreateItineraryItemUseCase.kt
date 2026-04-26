package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class CreateItineraryItemUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(item: ItineraryItem): Result<Unit> {
        return repository.createItineraryItem(item)
    }
}
