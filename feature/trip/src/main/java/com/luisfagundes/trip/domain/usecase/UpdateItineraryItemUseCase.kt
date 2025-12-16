package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ItineraryItem
import com.luisfagundes.trip.domain.repository.TripItineraryRepository
import javax.inject.Inject

internal class UpdateItineraryItemUseCase @Inject constructor(
    private val repository: TripItineraryRepository
) {
    suspend operator fun invoke(item: ItineraryItem): Result<Unit> {
        return repository.updateItineraryItem(item)
    }
}
