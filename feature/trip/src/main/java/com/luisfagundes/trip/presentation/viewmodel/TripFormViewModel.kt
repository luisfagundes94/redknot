package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.errorOrNull
import com.luisfagundes.trip.domain.usecase.CreateTripUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDestinationUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDateUseCase
import com.luisfagundes.trip.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.trip.presentation.effect.TripFormUiEffect
import com.luisfagundes.trip.presentation.state.TripFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class TripFormViewModel @Inject constructor(
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateDestinationUseCase: ValidateDestinationUseCase,
    private val createTripUseCase: CreateTripUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(TripFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<TripFormUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onTitleChange(title: String) {
        _uiState.update { state ->
            state.copy(
                title = title,
                titleError = validateTitleUseCase(title).errorOrNull()
            )
        }
    }

    fun onStartDateChange(startDate: LocalDate?) {
        _uiState.update { state ->
            state.copy(
                startDate = startDate,
                startDateError = validateDateUseCase(startDate).errorOrNull()
            )
        }
    }

    fun onEndDateChange(endDate: LocalDate?) {
        _uiState.update { state ->
            state.copy(
                endDate = endDate,
                endDateError = validateDateUseCase(endDate).errorOrNull()
            )
        }
    }

    fun onDestinationChange(destination: String) {
        _uiState.update { state ->
            state.copy(
                destination = destination,
                destinationError = validateDestinationUseCase(destination).errorOrNull()
            )
        }
    }

    fun onSubmit() {
        val state = _uiState.value

        viewModelScope.launch(dispatcher) {
            createTripUseCase.invoke(
                trip = Trip(
                    id = 0,
                    title = state.title,
                    location = state.destination,
                    startDate = state.startDate ?: return@launch,
                    endDate = state.endDate ?: return@launch,
                    imageUrl = "https://images.unsplash.com/photo-1528041119984-da3a9f8d04d1?q=80&w=2818&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                    done = false
                )
            ).fold(
                onSuccess = {
                    _uiEffect.send(TripFormUiEffect.NavigateBack)
                },
                onFailure = { error ->
                    _uiEffect.send(TripFormUiEffect.ShowErrorToast(error.toString()))
                }
            )
        }
    }
}