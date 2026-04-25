package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.core.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateItineraryDateUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateItineraryTitleUseCase
import com.luisfagundes.itinerary.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ActivityFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ActivityFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class ActivityFormViewModel @Inject constructor(
    private val validateTitleUseCase: ValidateItineraryTitleUseCase,
    private val validateDateUseCase: ValidateItineraryDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<ActivityFormUiState, ActivityFormUiEffect>() {
    override fun initialState() = ActivityFormUiState()
    fun onTitleChange(title: String) {
        setState { it.copy(title = title, titleError = validateTitleUseCase(title)) }
    }

    fun onDescriptionChange(description: String) {
        setState { it.copy(description = description) }
    }

    fun onLocationChange(location: String) {
        setState { it.copy(location = location) }
    }

    fun onDateChange(date: LocalDate?) {
        setState { it.copy(date = date, dateError = validateDateUseCase(date)) }
    }

    fun onTimeChange(time: LocalTime) {
        setState { it.copy(time = time, timeError = validateTimeUseCase(time)) }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        setState { it.copy(isLoading = true) }

        val activity = createActivity(tripId)

        createItineraryItemUseCase(activity).fold(
            onSuccess = { sendEffect { ActivityFormUiEffect.NavigateBack } },
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
