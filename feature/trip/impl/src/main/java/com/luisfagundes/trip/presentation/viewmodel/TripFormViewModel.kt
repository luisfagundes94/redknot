package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.usecase.GetUnsplashImageUseCase
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.model.errorOrNull
import com.luisfagundes.trip.domain.usecase.CreateTripUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDateUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDestinationUseCase
import com.luisfagundes.trip.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.trip.presentation.viewmodel.effect.TripFormUiEffect
import com.luisfagundes.trip.presentation.viewmodel.state.TripFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
) : ViewModel<TripFormUiState, TripFormUiEffect>(
    initialState = TripFormUiState()
) {
    fun onTitleChange(title: String) {
        setState { state ->
            state.copy(
                title = title,
                titleError = validateTitleUseCase(title).errorOrNull()
            )
        }
    }

    fun onStartDateChange(startDate: LocalDate?) {
        setState { state ->
            state.copy(
                startDate = startDate,
                startDateError = validateDateUseCase(startDate).errorOrNull()
            )
        }
    }

    fun onEndDateChange(endDate: LocalDate?) {
        setState { state ->
            state.copy(
                endDate = endDate,
                endDateError = validateDateUseCase(endDate).errorOrNull()
            )
        }
    }

    fun onDestinationChange(destination: String) {
        setState { state ->
            state.copy(
                destination = destination,
                destinationError = validateDestinationUseCase(destination).errorOrNull()
            )
        }
    }

    fun onSubmit() = viewModelScope.launch(dispatcher) {
        val state = getCurrentState()

        setState { it.copy(isLoading = true) }

        val imageUrl = getUnsplashImageUseCase(state.destination)
            .getOrNull()
            .orEmpty()

        val trip = state.toTripWith(imageUrl)

        createTripUseCase(trip).fold(
            onSuccess = { sendEffect { TripFormUiEffect.NavigateBack } },
            onFailure = { sendEffect { TripFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setState { it.copy(isLoading = false) }
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