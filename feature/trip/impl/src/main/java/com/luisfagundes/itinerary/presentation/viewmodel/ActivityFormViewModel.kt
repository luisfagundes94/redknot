package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.common.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.common.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ActivityFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ActivityFormUiState
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class ActivityFormViewModel @Inject constructor(
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<ActivityFormUiState, ActivityFormUiEffect>(
    initialState = ActivityFormUiState()
) {
    fun onTitleChange(title: String) {
        setState { currentState ->
            currentState.copy(
                title = title,
                titleError = validateTitleUseCase(title).errorOrNull()
            )
        }
    }

    fun onDescriptionChange(description: String) {
        setState { currentState ->
            currentState.copy(description = description)
        }
    }

    fun onLocationChange(location: String) {
        setState { currentState ->
            currentState.copy(location = location)
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

        val activity = createActivity(tripId)

        createItineraryItemUseCase(activity).fold(
            onSuccess = { sendEffect { ActivityFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { ActivityFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setState { it.copy(isLoading = false) }
    }

    private fun createActivity(tripId: Int): Activity {
        val state = getCurrentState()

        return Activity(
            id = UUID.randomUUID().toString(),
            tripId = tripId,
            date = state.date ?: LocalDate.now(),
            time = state.time ?: LocalTime.now(),
            title = state.title,
            description = state.description.ifBlank { null },
            location = state.location.ifBlank { null },
            imageUrl = null
        )
    }
}
