package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ItineraryItem
import com.luisfagundes.trip.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class UpdateItineraryItemUseCase @Inject constructor(
    private val repository: ItineraryRepository
) {
    suspend operator fun invoke(item: ItineraryItem): Result<Unit> {
        return repository.updateItineraryItem(item)
    }
}
