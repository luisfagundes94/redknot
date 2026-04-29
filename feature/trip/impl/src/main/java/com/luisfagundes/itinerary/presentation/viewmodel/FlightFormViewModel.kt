package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Airport
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateDurationErrorUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateFlightNumberUseCase
import com.luisfagundes.common.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.FlightFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.FlightFormUiState
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
internal class FlightFormViewModel @Inject constructor(
    private val validateFlightNumberUseCase: ValidateFlightNumberUseCase,
    private val validateDurationErrorUseCase: ValidateDurationErrorUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<FlightFormUiState, FlightFormUiEffect>(
    initialState = FlightFormUiState()
) {
    fun onFlightNumberChange(value: String) {
        setState { currentState ->
            currentState.copy(
                flightNumber = value,
                flightNumberError = validateFlightNumberUseCase(value).errorOrNull()
            )
        }
    }

    fun onOriginChange(city: String) {
        setState { currentState ->
            currentState.copy(originAirportCity = city)
        }
    }

    fun onDestinationChange(city: String) {
        setState { currentState ->
            currentState.copy(destinationAirportCity = city)
        }
    }

    fun onDurationChange(hoursStr: String, minutesStr: String) {
        val hour = hoursStr.toIntOrNull() ?: 0
        val minutes = minutesStr.toIntOrNull() ?: 0

        setState { currentState ->
            currentState.copy(
                durationHours = hoursStr,
                durationMinutes = minutesStr,
                durationError = validateDurationErrorUseCase(hour, minutes).errorOrNull()
            )
        }
    }

    fun onSeatNumberChange(value: String) {
        setState { currentState ->
            currentState.copy(seatNumber = value)
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

    fun onCompanyNameChange(name: String) {
        setState { currentState ->
            currentState.copy(companyName = name)
        }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        setState { it.copy(isLoading = true) }

        val flight = createFlight(tripId)

        createItineraryItemUseCase(flight).fold(
            onSuccess = { sendEffect { FlightFormUiEffect.NavigateToTripDetails } },
            onFailure = { sendEffect { FlightFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setState { it.copy(isLoading = false) }
    }

    private fun createFlight(tripId: Int): Flight {
        val state = getCurrentState()

        val hours = (state.durationHours.toIntOrNull() ?: 0).hours
        val minutes = (state.durationMinutes.toIntOrNull() ?: 0).minutes

        return Flight(
            id = UUID.randomUUID().toString(),
            tripId = tripId,
            date = state.date ?: LocalDate.now(),
            time = state.time ?: LocalTime.now(),
            flightNumber = state.flightNumber,
            companyName = state.companyName,
            origin = Airport(name = null, city = state.originAirportCity),
            destination = Airport(name = null, city = state.destinationAirportCity),
            duration = hours + minutes,
            seatNumber = state.seatNumber
        )
    }
}
