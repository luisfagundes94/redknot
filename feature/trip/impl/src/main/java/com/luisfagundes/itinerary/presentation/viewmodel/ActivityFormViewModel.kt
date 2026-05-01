package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.DeleteItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemByIdUseCase
import com.luisfagundes.itinerary.domain.usecase.UpdateItineraryItemUseCase
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.common.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.common.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ActivityFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ActivityFormUiState
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import com.luisfagundes.trip.domain.usecase.GetTripStartDateUseCase
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
    private val getItineraryItemByIdUseCase: GetItineraryItemByIdUseCase,
    private val updateItineraryItemUseCase: UpdateItineraryItemUseCase,
    private val deleteItineraryItemUseCase: DeleteItineraryItemUseCase,
    private val getTripStartDateUseCase: GetTripStartDateUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<ActivityFormUiState, ActivityFormUiEffect>(
    initialState = ActivityFormUiState.Loading
) {
    fun initForm(tripId: Int, itemId: String?) {
        viewModelScope.launch(dispatcher) {
            val tripStartDate = getTripStartDateUseCase(tripId)

            if (itemId == null) {
                setState { ActivityFormUiState.Content(tripStartDate = tripStartDate) }
                return@launch
            }

            getItineraryItemByIdUseCase(itemId, ItineraryItemType.ACTIVITY).fold(
                onSuccess = { item ->
                    val activity = item as? Activity ?: return@fold
                    setState {
                        ActivityFormUiState.Content(
                            editingItemId = activity.id,
                            title = activity.title,
                            description = activity.description.orEmpty(),
                            location = activity.location.orEmpty(),
                            date = activity.date,
                            time = activity.time,
                            tripStartDate = tripStartDate,
                        )
                    }
                },
                onFailure = {
                    sendEffect { ActivityFormUiEffect.ShowErrorToast(it.toString()) }
                }
            )
        }
    }

    fun onTitleChange(title: String) {
        setStateOf<ActivityFormUiState.Content> {
            it.copy(
                title = title,
                titleError = validateTitleUseCase(title).errorOrNull()
            )
        }
    }

    fun onDescriptionChange(description: String) {
        setStateOf<ActivityFormUiState.Content> { it.copy(description = description) }
    }

    fun onLocationChange(location: String) {
        setStateOf<ActivityFormUiState.Content> { it.copy(location = location) }
    }

    fun onDateChange(date: LocalDate?) {
        setStateOf<ActivityFormUiState.Content> {
            it.copy(
                date = date,
                dateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    fun onTimeChange(time: LocalTime) {
        setStateOf<ActivityFormUiState.Content> {
            it.copy(
                time = time,
                timeError = validateTimeUseCase(time).errorOrNull()
            )
        }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? ActivityFormUiState.Content ?: return@launch
        setStateOf<ActivityFormUiState.Content> { it.copy(isLoading = true) }

        val activity = buildActivity(tripId, content)

        val result = if (content.editingItemId == null) {
            createItineraryItemUseCase(activity)
        } else {
            updateItineraryItemUseCase(activity)
        }

        result.fold(
            onSuccess = { sendEffect { ActivityFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { ActivityFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<ActivityFormUiState.Content> { it.copy(isLoading = false) }
    }

    fun onDelete() = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? ActivityFormUiState.Content ?: return@launch
        val itemId = content.editingItemId ?: return@launch

        setStateOf<ActivityFormUiState.Content> { it.copy(isLoading = true) }

        deleteItineraryItemUseCase(itemId, ItineraryItemType.ACTIVITY).fold(
            onSuccess = { sendEffect { ActivityFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { ActivityFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<ActivityFormUiState.Content> { it.copy(isLoading = false) }
    }

    private fun buildActivity(tripId: Int, content: ActivityFormUiState.Content) = Activity(
        id = content.editingItemId ?: UUID.randomUUID().toString(),
        tripId = tripId,
        date = content.date ?: LocalDate.now(),
        time = content.time ?: LocalTime.now(),
        title = content.title,
        description = content.description.ifBlank { null },
        location = content.location.ifBlank { null },
        imageUrl = null
    )
}
