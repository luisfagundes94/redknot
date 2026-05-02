package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Airport
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.itinerary.domain.usecase.ItineraryItemFormUseCase
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
    private val formUseCase: ItineraryItemFormUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<FlightFormUiState, FlightFormUiEffect>(
    initialState = FlightFormUiState.Loading
) {
    fun initForm(tripId: Int, itemId: String?) {
        viewModelScope.launch(dispatcher) {
            val tripStartDate = formUseCase.getTripStartDate(tripId)

            if (itemId == null) {
                setState { FlightFormUiState.Content(tripStartDate = tripStartDate) }
                return@launch
            }

            formUseCase.getItemById(itemId, ItineraryItemType.FLIGHT).fold(
                onSuccess = { item ->
                    val flight = item as? Flight ?: return@fold
                    val totalMinutes = flight.duration.inWholeMinutes
                    setState {
                        FlightFormUiState.Content(
                            editingItemId = flight.id,
                            flightNumber = flight.flightNumber,
                            companyName = flight.companyName,
                            originAirportCity = flight.origin.city,
                            destinationAirportCity = flight.destination.city,
                            durationHours = (totalMinutes / 60).toString(),
                            durationMinutes = (totalMinutes % 60).toString(),
                            seatNumber = flight.seatNumber,
                            date = flight.date,
                            time = flight.time,
                            tripStartDate = tripStartDate,
                        )
                    }
                },
                onFailure = {
                    sendEffect { FlightFormUiEffect.ShowErrorToast(it.toString()) }
                }
            )
        }
    }

    fun onFlightNumberChange(value: String) {
        setStateOf<FlightFormUiState.Content> {
            it.copy(
                flightNumber = value,
                flightNumberError = validateFlightNumberUseCase(value).errorOrNull()
            )
        }
    }

    fun onOriginChange(city: String) {
        setStateOf<FlightFormUiState.Content> { it.copy(originAirportCity = city) }
    }

    fun onDestinationChange(city: String) {
        setStateOf<FlightFormUiState.Content> { it.copy(destinationAirportCity = city) }
    }

    fun onDurationChange(hoursStr: String, minutesStr: String) {
        val hour = hoursStr.toIntOrNull() ?: 0
        val mins = minutesStr.toIntOrNull() ?: 0

        setStateOf<FlightFormUiState.Content> {
            it.copy(
                durationHours = hoursStr,
                durationMinutes = minutesStr,
                durationError = validateDurationErrorUseCase(hour, mins).errorOrNull()
            )
        }
    }

    fun onSeatNumberChange(value: String) {
        setStateOf<FlightFormUiState.Content> { it.copy(seatNumber = value) }
    }

    fun onDateChange(date: LocalDate?) {
        setStateOf<FlightFormUiState.Content> {
            it.copy(
                date = date,
                dateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    fun onTimeChange(time: LocalTime) {
        setStateOf<FlightFormUiState.Content> {
            it.copy(
                time = time,
                timeError = validateTimeUseCase(time).errorOrNull()
            )
        }
    }

    fun onCompanyNameChange(name: String) {
        setStateOf<FlightFormUiState.Content> { it.copy(companyName = name) }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? FlightFormUiState.Content ?: return@launch
        setStateOf<FlightFormUiState.Content> { it.copy(isLoading = true) }

        val flight = buildFlight(tripId, content)

        formUseCase.submitItem(flight, content.isEditMode).fold(
            onSuccess = { sendEffect { FlightFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { FlightFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<FlightFormUiState.Content> { it.copy(isLoading = false) }
    }

    fun onDelete() = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? FlightFormUiState.Content ?: return@launch
        val itemId = content.editingItemId ?: return@launch

        setStateOf<FlightFormUiState.Content> { it.copy(isLoading = true) }

        formUseCase.deleteItem(itemId, ItineraryItemType.FLIGHT).fold(
            onSuccess = { sendEffect { FlightFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { FlightFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<FlightFormUiState.Content> { it.copy(isLoading = false) }
    }

    private fun buildFlight(tripId: Int, content: FlightFormUiState.Content): Flight {
        val h = (content.durationHours.toIntOrNull() ?: 0).hours
        val m = (content.durationMinutes.toIntOrNull() ?: 0).minutes

        return Flight(
            id = content.editingItemId ?: UUID.randomUUID().toString(),
            tripId = tripId,
            date = content.date ?: LocalDate.now(),
            time = content.time ?: LocalTime.now(),
            flightNumber = content.flightNumber,
            companyName = content.companyName,
            origin = Airport(name = null, city = content.originAirportCity),
            destination = Airport(name = null, city = content.destinationAirportCity),
            duration = h + m,
            seatNumber = content.seatNumber
        )
    }
}
