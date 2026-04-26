package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.core.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateAddressUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateItineraryDateUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateNameUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.AccommodationFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.AccommodationFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class AccommodationFormViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateAddressUseCase: ValidateAddressUseCase,
    private val validateDateUseCase: ValidateItineraryDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<AccommodationFormUiState, AccommodationFormUiEffect>(
    initialState = AccommodationFormUiState()
) {
    fun onNameChange(name: String) {
        setState { it.copy(name = name, nameError = validateNameUseCase(name)) }
    }

    fun onAddressChange(address: String) {
        setState { it.copy(address = address, addressError = validateAddressUseCase(address)) }
    }

    fun onCheckInTypeChange(checkInType: CheckInType) {
        setState { it.copy(checkInType = checkInType) }
    }

    fun onDateChange(date: LocalDate?) {
        setState { it.copy(date = date, dateError = validateDateUseCase(date)) }
    }

    fun onTimeChange(time: LocalTime) {
        setState { it.copy(time = time, timeError = validateTimeUseCase(time)) }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        setState { it.copy(isLoading = true) }

        val accommodation = createAccommodation(tripId)

        createItineraryItemUseCase(accommodation).fold(
            onSuccess = { sendEffect { AccommodationFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { AccommodationFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setState { it.copy(isLoading = false) }
    }

    private fun createAccommodation(tripId: Int): Accommodation {
        val state = getCurrentState()

        return Accommodation(
            id = UUID.randomUUID().toString(),
            tripId = tripId,
            date = state.date ?: LocalDate.now(),
            time = state.time ?: LocalTime.now(),
            name = state.name,
            address = state.address,
            checkInType = state.checkInType,
            imageUrl = ""
        )
    }
}
