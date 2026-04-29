package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.common.domain.usecase.ValidateAddressUseCase
import com.luisfagundes.common.domain.usecase.ValidateNameUseCase
import com.luisfagundes.common.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.AccommodationFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.AccommodationFormUiState
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
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
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<AccommodationFormUiState, AccommodationFormUiEffect>(
    initialState = AccommodationFormUiState()
) {
    fun onNameChange(name: String) {
        setState { currentState ->
            currentState.copy(
                name = name,
                nameError = validateNameUseCase(name).errorOrNull()
            )
        }
    }

    fun onAddressChange(address: String) {
        setState { currentState ->
            currentState.copy(
                address = address,
                addressError = validateAddressUseCase(address).errorOrNull()
            )
        }
    }

    fun onCheckInTypeChange(checkInType: CheckInType) {
        setState { currentState ->
            currentState.copy(checkInType = checkInType)
        }
    }

    fun onDateChange(date: LocalDate?) {
        setState { currentState ->
            currentState.copy(
                date = date,
                dateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    fun onTimeChange(time: LocalTime) {
        setState { currentState ->
            currentState.copy(
                time = time,
                timeError = validateTimeUseCase(time).errorOrNull()
            )
        }
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
