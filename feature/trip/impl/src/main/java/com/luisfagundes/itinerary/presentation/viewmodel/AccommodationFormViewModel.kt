package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.itinerary.domain.usecase.ItineraryItemFormUseCase
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
    private val formUseCase: ItineraryItemFormUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<AccommodationFormUiState, AccommodationFormUiEffect>(
    initialState = AccommodationFormUiState.Loading
) {
    fun initForm(tripId: Int, itemId: String?) {
        viewModelScope.launch(dispatcher) {
            val tripStartDate = formUseCase.getTripStartDate(tripId)

            if (itemId == null) {
                setState { AccommodationFormUiState.Content(tripStartDate = tripStartDate) }
                return@launch
            }

            formUseCase.getItemById(itemId, ItineraryItemType.ACCOMMODATION).fold(
                onSuccess = { item ->
                    val accommodation = item as? Accommodation ?: return@fold
                    setState {
                        AccommodationFormUiState.Content(
                            editingItemId = accommodation.id,
                            name = accommodation.name,
                            address = accommodation.address,
                            checkInType = accommodation.checkInType,
                            date = accommodation.date,
                            time = accommodation.time,
                            tripStartDate = tripStartDate,
                        )
                    }
                },
                onFailure = {
                    sendEffect { AccommodationFormUiEffect.ShowErrorToast(it.toString()) }
                }
            )
        }
    }

    fun onNameChange(name: String) {
        setStateOf<AccommodationFormUiState.Content> {
            it.copy(
                name = name,
                nameError = validateNameUseCase(name).errorOrNull()
            )
        }
    }

    fun onAddressChange(address: String) {
        setStateOf<AccommodationFormUiState.Content> {
            it.copy(
                address = address,
                addressError = validateAddressUseCase(address).errorOrNull()
            )
        }
    }

    fun onCheckInTypeChange(checkInType: CheckInType) {
        setStateOf<AccommodationFormUiState.Content> { it.copy(checkInType = checkInType) }
    }

    fun onDateChange(date: LocalDate?) {
        setStateOf<AccommodationFormUiState.Content> {
            it.copy(
                date = date,
                dateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    fun onTimeChange(time: LocalTime) {
        setStateOf<AccommodationFormUiState.Content> {
            it.copy(
                time = time,
                timeError = validateTimeUseCase(time).errorOrNull()
            )
        }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? AccommodationFormUiState.Content ?: return@launch
        setStateOf<AccommodationFormUiState.Content> { it.copy(isLoading = true) }

        val accommodation = buildAccommodation(tripId, content)

        formUseCase.submitItem(accommodation, content.isEditMode).fold(
            onSuccess = { sendEffect { AccommodationFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { AccommodationFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<AccommodationFormUiState.Content> { it.copy(isLoading = false) }
    }

    fun onDelete() = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? AccommodationFormUiState.Content ?: return@launch
        val itemId = content.editingItemId ?: return@launch

        setStateOf<AccommodationFormUiState.Content> { it.copy(isLoading = true) }

        formUseCase.deleteItem(itemId, ItineraryItemType.ACCOMMODATION).fold(
            onSuccess = { sendEffect { AccommodationFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { AccommodationFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<AccommodationFormUiState.Content> { it.copy(isLoading = false) }
    }

    private fun buildAccommodation(
        tripId: Int,
        content: AccommodationFormUiState.Content
    ) = Accommodation(
        id = content.editingItemId ?: UUID.randomUUID().toString(),
        tripId = tripId,
        date = content.date ?: LocalDate.now(),
        time = content.time ?: LocalTime.now(),
        name = content.name,
        address = content.address,
        checkInType = content.checkInType,
        imageUrl = ""
    )
}
