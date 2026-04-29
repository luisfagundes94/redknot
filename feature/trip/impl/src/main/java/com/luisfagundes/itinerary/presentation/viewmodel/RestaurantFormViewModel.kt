package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.MealType
import com.luisfagundes.itinerary.domain.model.Restaurant
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.common.domain.usecase.ValidateNameUseCase
import com.luisfagundes.common.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.RestaurantFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.RestaurantFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class RestaurantFormViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateTimeUseCase: ValidateTimeUseCase,
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<RestaurantFormUiState, RestaurantFormUiEffect>(
    initialState = RestaurantFormUiState()
) {
    fun onNameChange(name: String) {
        setState { currentState ->
            currentState.copy(
                name = name,
                nameError = validateNameUseCase(name).errorOrNull()
            )
        }
    }

    fun onAddressChange(address: String) {
        setState { it.copy(address = address) }
    }
    
    fun onMealTypeChange(mealType: MealType) {
        setState { it.copy(mealType = mealType) }
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

        val restaurant = createRestaurant(tripId)

        createItineraryItemUseCase(restaurant).fold(
            onSuccess = { sendEffect { RestaurantFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setState { it.copy(isLoading = false) }
    }

    private fun createRestaurant(tripId: Int): Restaurant {
        val state = getCurrentState()

        return Restaurant(
            id = UUID.randomUUID().toString(),
            tripId = tripId,
            date = state.date ?: LocalDate.now(),
            time = state.time ?: LocalTime.now(),
            name = state.name,
            address = state.address,
            mealType = state.mealType
        )
    }
}
