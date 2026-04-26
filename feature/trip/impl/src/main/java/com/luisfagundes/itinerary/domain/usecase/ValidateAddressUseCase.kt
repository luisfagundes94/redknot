package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import javax.inject.Inject

internal class ValidateAddressUseCase @Inject constructor() {
    operator fun invoke(address: String): ItineraryValidationError? {
        return if (address.isBlank()) ItineraryValidationError.EMPTY_ADDRESS
        else null
    }
}
