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
import com.luisfagundes.itinerary.domain.usecase.CreateItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.DeleteItineraryItemUseCase
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemByIdUseCase
import com.luisfagundes.itinerary.domain.usecase.UpdateItineraryItemUseCase
import com.luisfagundes.common.domain.usecase.ValidateNameUseCase
import com.luisfagundes.common.domain.usecase.ValidateTimeUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.RestaurantFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.RestaurantFormUiState
import com.luisfagundes.itinerary.presentation.viewmodel.state.RestaurantFormUiState.Content
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
    private val createItineraryItemUseCase: CreateItineraryItemUseCase,
    private val getItineraryItemByIdUseCase: GetItineraryItemByIdUseCase,
    private val updateItineraryItemUseCase: UpdateItineraryItemUseCase,
    private val deleteItineraryItemUseCase: DeleteItineraryItemUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<RestaurantFormUiState, RestaurantFormUiEffect>(
    initialState = RestaurantFormUiState.Loading
) {
    fun initForm(itemId: String?) {
        if (itemId == null) {
            setState { Content() }
            return
        }
        viewModelScope.launch(dispatcher) {
            getItineraryItemByIdUseCase(itemId, ItineraryItemType.RESTAURANT).fold(
                onSuccess = { item ->
                    val restaurant = item as? Restaurant ?: return@fold
                    setState {
                        Content(
                            editingItemId = restaurant.id,
                            name = restaurant.name,
                            address = restaurant.address,
                            mealType = restaurant.mealType,
                            date = restaurant.date,
                            time = restaurant.time
                        )
                    }
                },
                onFailure = {
                    sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) }
                }
            )
        }
    }

    fun onNameChange(name: String) {
        setStateOf<Content> {
            it.copy(
                name = name,
                nameError = validateNameUseCase(name).errorOrNull()
            )
        }
    }

    fun onAddressChange(address: String) {
        setStateOf<Content> {
            it.copy(
                address = address,
                addressError = validateAddressUseCase(address).errorOrNull()
            )
        }
    }

    fun onMealTypeChange(mealType: MealType) {
        setStateOf<Content> { it.copy(mealType = mealType) }
    }

    fun onDateChange(date: LocalDate?) {
        setStateOf<Content> {
            it.copy(
                date = date,
                dateError = validateDateUseCase(date).errorOrNull()
            )
        }
    }

    fun onTimeChange(time: LocalTime) {
        setStateOf<Content> {
            it.copy(
                time = time,
                timeError = validateTimeUseCase(time).errorOrNull()
            )
        }
    }

    fun onSubmit(tripId: Int) = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? Content ?: return@launch
        setStateOf<Content> { it.copy(isLoading = true) }

        val restaurant = buildRestaurant(tripId, content)
        val result = if (content.editingItemId == null) {
            createItineraryItemUseCase(restaurant)
        } else {
            updateItineraryItemUseCase(restaurant)
        }

        result.fold(
            onSuccess = { sendEffect { RestaurantFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<Content> { it.copy(isLoading = false) }
    }

    fun onDelete() = viewModelScope.launch(dispatcher) {
        val content = getCurrentState() as? Content ?: return@launch
        val itemId = content.editingItemId ?: return@launch
        setStateOf<Content> { it.copy(isLoading = true) }

        deleteItineraryItemUseCase(itemId, ItineraryItemType.RESTAURANT).fold(
            onSuccess = { sendEffect { RestaurantFormUiEffect.NavigateBackToTripDetails } },
            onFailure = { sendEffect { RestaurantFormUiEffect.ShowErrorToast(it.toString()) } }
        )

        setStateOf<Content> { it.copy(isLoading = false) }
    }

    private fun buildRestaurant(tripId: Int, content: Content) = Restaurant(
        id = content.editingItemId ?: UUID.randomUUID().toString(),
        tripId = tripId,
        date = content.date ?: LocalDate.now(),
        time = content.time ?: LocalTime.now(),
        name = content.name,
        address = content.address,
        mealType = content.mealType
    )
}
