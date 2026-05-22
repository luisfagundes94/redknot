package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.common.domain.model.errorOrNull
import com.luisfagundes.common.domain.usecase.ValidateAddressUseCase
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.itinerary.domain.model.MealType
import com.luisfagundes.itinerary.domain.model.Restaurant
import com.luisfagundes.itinerary.domain.usecase.ItineraryItemFormUseCase
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
    private val validateAddressUseCase: ValidateAddressUseCase,
    private val formUseCase: ItineraryItemFormUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<RestaurantFormUiState, RestaurantFormUiEffect>(
    initialState = RestaurantFormUiState.Loading
) {
    fun initForm(tripId: Int, itemId: String?) {
        viewModelScope.launch(dispatcher) {
            val tripStartDate = formUseCase.getTripStartDate(tripId)

            if (itemId == null) {
                setState { RestaurantFormUiState.Content(tripStartDate = tripStartDate) }
                return@launch
            }

            formUseCase.getItemById(itemId, ItineraryItemType.RESTAURANT).fold(
                onSuccess = { item ->
                    val restaurant = item as? Restaurant ?: return@fold
                    setState {
                        RestaurantFormUiState.Content(
                            editingItemId = restaurant.id,
                            name = restaurant.name,
                            address = restaurant.address,
                            mealType = restaurant.mealType,
                            date = restaurant.date,
                            time = restaurant.time,
                            tripStartDate = tripStartDate,
                        )
                    }
                },
                onFailure = {
                    sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) }
                }
            )
        }
    }

    fun updateName(name: String) {
        setStateOf<RestaurantFormUiState.Content> {
            it.copy(
                name = name,
                nameError = validateNameUseCase(name).errorOrNull()
            )
        }
    }

    fun updateAddress(address: String) {
        setStateOf<RestaurantFormUiState.Content> {
            it.copy(
                address = address,
                addressError = validateAddressUseCase(address).errorOrNull()
            )
        }
    }

    fun updateMealType(mealType: MealType) {
        setStateOf<RestaurantFormUiState.Content> { it.copy(mealType = mealType) }
    }

    fun updateDate(date: LocalDate?) {
        setStateOf<RestaurantFormUiState.Content> {
            it.copy(
                date = date,
                dateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    fun updateTime(time: LocalTime) {
        setStateOf<RestaurantFormUiState.Content> {
            it.copy(
                time = time,
                timeError = validateTimeUseCase(time).errorOrNull()
            )
        }
    }

    fun submit(tripId: Int) = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? RestaurantFormUiState.Content ?: return@launch
        setStateOf<RestaurantFormUiState.Content> { it.copy(isLoading = true) }

        val restaurant = buildRestaurant(tripId, content)

        formUseCase.submitItem(restaurant, content.isEditMode).fold(
            onSuccess = { sendEffect { RestaurantFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<RestaurantFormUiState.Content> { it.copy(isLoading = false) }
    }

    fun deleteRestaurant() = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? RestaurantFormUiState.Content ?: return@launch
        val itemId = content.editingItemId ?: return@launch

        setStateOf<RestaurantFormUiState.Content> { it.copy(isLoading = true) }

        formUseCase.deleteItem(itemId, ItineraryItemType.RESTAURANT).fold(
            onSuccess = { sendEffect { RestaurantFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<RestaurantFormUiState.Content> { it.copy(isLoading = false) }
    }

    private fun buildRestaurant(tripId: Int, content: RestaurantFormUiState.Content) = Restaurant(
        id = content.editingItemId ?: UUID.randomUUID().toString(),
        tripId = tripId,
        date = content.date ?: LocalDate.now(),
        time = content.time ?: LocalTime.now(),
        name = content.name,
        address = content.address,
        mealType = content.mealType
    )
}
