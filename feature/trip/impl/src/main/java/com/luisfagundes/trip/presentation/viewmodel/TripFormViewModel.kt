package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.model.errorOrNull
import com.luisfagundes.trip.domain.usecase.CreateTripUseCase
import com.luisfagundes.common.domain.usecase.GetUnsplashImageUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDestinationUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDateUseCase
import com.luisfagundes.trip.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.trip.presentation.viewmodel.effect.TripFormUiEffect
import com.luisfagundes.trip.presentation.viewmodel.state.TripFormUiState
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
    private val getUnsplashImageUseCase: GetUnsplashImageUseCase,
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

    fun onSubmit() = viewModelScope.launch(dispatcher) {
        val state = _uiState.value

        _uiState.update { it.copy(isLoading = true) }

        val imageUrl = getUnsplashImageUseCase(state.destination)
            .getOrNull()
            .orEmpty()

        val trip = state.toTripWith(imageUrl)

        createTripUseCase(trip).fold(
            onSuccess = { _uiEffect.send(TripFormUiEffect.NavigateBack) },
            onFailure = { _uiEffect.send(TripFormUiEffect.ShowErrorToast(it.toString())) }
        )

        _uiState.update { it.copy(isLoading = false) }
    }

    private fun TripFormUiState.toTripWith(imageUrl: String) = Trip(
        id = 0,
        title = title,
        location = destination,
        startDate = startDate ?: LocalDate.now(),
        endDate = endDate ?: LocalDate.now(),
        imageUrl = imageUrl,
        status = TripStatus.UNSCHEDULED
    )
}