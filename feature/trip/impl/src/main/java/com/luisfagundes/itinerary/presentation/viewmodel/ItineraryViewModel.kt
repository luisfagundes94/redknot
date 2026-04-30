package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemsByDayUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ItineraryViewModel @Inject constructor(
    private val getItineraryItemsByDayUseCase: GetItineraryItemsByDayUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel<ItineraryUiState, ItineraryUiEffect>(
    initialState = ItineraryUiState.Loading
) {
    fun getItineraryItemsByDay(tripId: Int) = viewModelScope.launch(dispatcher) {
        getItineraryItemsByDayUseCase(tripId).onSuccess { itemsByDay ->
            setState {
                if (itemsByDay.isEmpty()) ItineraryUiState.Empty
                else ItineraryUiState.Content(itemsByDay)
            }
        }
    }

    fun onAddItineraryItem() {
        sendEffect { ItineraryUiEffect.NavigateToItineraryItemForm }
    }

    fun onItineraryItemClick(item: ItineraryItem) {
        sendEffect { ItineraryUiEffect.NavigateToEditItineraryItem(item) }
    }
}