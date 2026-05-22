package com.luisfagundes.itinerary.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import com.luisfagundes.itinerary.domain.model.CheckInType
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface AccommodationFormUiEvent : UiEvent {
    data class InitForm(val tripId: Int, val itemId: String?) : AccommodationFormUiEvent
    data class UpdateName(val name: String) : AccommodationFormUiEvent
    data class UpdateAddress(val address: String) : AccommodationFormUiEvent
    data class UpdateCheckInType(val checkInType: CheckInType) : AccommodationFormUiEvent
    data class UpdateDate(val date: LocalDate?) : AccommodationFormUiEvent
    data class UpdateTime(val time: LocalTime) : AccommodationFormUiEvent
    data object NavigateBack : AccommodationFormUiEvent
    data class Submit(val tripId: Int) : AccommodationFormUiEvent
    data object DeleteAccommodation : AccommodationFormUiEvent
}
