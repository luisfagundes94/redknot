package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.core.presentation.arch.ViewModel
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemListUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.action.ItineraryUiAction
import com.luisfagundes.itinerary.presentation.viewmodel.event.ItineraryUiEvent
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ItineraryViewModel @Inject constructor(
    private val getItineraryItemListUseCase: GetItineraryItemListUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel<ItineraryUiState, ItineraryUiEvent, ItineraryUiAction>() {
    override fun initialState() = ItineraryUiState.Loading

    override fun dispatchEvent(event: ItineraryUiEvent) {
        when (event) {
            is ItineraryUiEvent.OnNewItineraryItemClick -> onNewItineraryItemClick()
            is ItineraryUiEvent.OnGetItineraryList -> getItineraryList(event.tripId)
        }
    }
    private fun getItineraryList(tripId: Int) = viewModelScope.launch(dispatcher) {
        getItineraryItemListUseCase(tripId).onSuccess { itineraryItemList ->
            setState {
                if (itineraryItemList.isEmpty()) {
                    ItineraryUiState.Empty
                } else {
                    ItineraryUiState.Content(itineraryItemList)
                }
            }
        }
    }

    private fun onNewItineraryItemClick() {
        sendAction { ItineraryUiAction.NavigateToItineraryItemForm }
    }
}