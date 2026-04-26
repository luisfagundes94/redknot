package com.luisfagundes.common.domain.usecase

import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class GetUnsplashImageUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(location: String): Result<String> {
        return repository.getTripImageUrl(location)
    }
}