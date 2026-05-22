package com.luisfagundes.itinerary.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import com.luisfagundes.itinerary.domain.model.MealType
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface RestaurantFormUiEvent : UiEvent {
    data class InitForm(val tripId: Int, val itemId: String?) : RestaurantFormUiEvent
    data class UpdateName(val name: String) : RestaurantFormUiEvent
    data class UpdateAddress(val address: String) : RestaurantFormUiEvent
    data class UpdateMealType(val mealType: MealType) : RestaurantFormUiEvent
    data class UpdateDate(val date: LocalDate?) : RestaurantFormUiEvent
    data class UpdateTime(val time: LocalTime) : RestaurantFormUiEvent
    data object NavigateBack : RestaurantFormUiEvent
    data class Submit(val tripId: Int) : RestaurantFormUiEvent
    data object DeleteRestaurant : RestaurantFormUiEvent
}
