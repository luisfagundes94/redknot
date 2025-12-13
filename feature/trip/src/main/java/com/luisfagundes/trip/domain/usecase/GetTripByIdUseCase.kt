package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class GetTripByIdUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(id: Int) = repository.getTripById(id)
}