package com.luisfagundes.itinerary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.core.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemListUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ItineraryViewModel @Inject constructor(
    private val getItineraryItemListUseCase: GetItineraryItemListUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel<ItineraryUiState, ItineraryUiEffect>() {
    override fun initialState() = ItineraryUiState.Loading

    fun getItineraryList(tripId: Int) = viewModelScope.launch(dispatcher) {
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

    fun onNewItineraryItemClick() {
        sendEffect { ItineraryUiEffect.NavigateToItineraryItemForm }
    }
}