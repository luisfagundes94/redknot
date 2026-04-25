package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.core.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Airport
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateDurationErrorUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateFlightNumberUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateItineraryDateUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.FlightFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.FlightFormUiState
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
    private val validateDateUseCase: ValidateItineraryDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<FlightFormUiState, FlightFormUiEffect>(
    initialState = FlightFormUiState()
) {
    fun onFlightNumberChange(value: String) {
        setState {
            it.copy(
                flightNumber = value,
                flightNumberError = validateFlightNumberUseCase(value)
            )
        }
    }

    fun onOriginChange(city: String) {
        setState { it.copy(originAirportCity = city) }
    }

    fun onDestinationChange(city: String) {
        setState { it.copy(destinationAirportCity = city) }
    }

    fun onDurationChange(hoursStr: String, minutesStr: String) {
        val hour = hoursStr.toIntOrNull() ?: 0
        val minutes = minutesStr.toIntOrNull() ?: 0

        setState {
            it.copy(
                durationHours = hoursStr,
                durationMinutes = minutesStr,
                durationError = validateDurationErrorUseCase(hour, minutes)
            )
        }
    }

    fun onSeatNumberChange(value: String) {
        setState { it.copy(seatNumber = value) }
    }

    fun onDateChange(date: LocalDate?) {
        setState { it.copy(date = date, dateError = validateDateUseCase(date)) }
    }

    fun onTimeChange(time: LocalTime) {
        setState { it.copy(time = time, timeError = validateTimeUseCase(time)) }
    }

    fun onCompanyNameChange(name: String) {
        setState { it.copy(companyName = name) }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        setState { it.copy(isLoading = true) }

        val flight = createFlight(tripId)

        createItineraryItemUseCase(flight).fold(
            onSuccess = { sendEffect { FlightFormUiEffect.NavigateBack } },
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
