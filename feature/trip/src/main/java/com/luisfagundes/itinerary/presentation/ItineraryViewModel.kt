package com.luisfagundes.itinerary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemListUseCase
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
): ViewModel() {
    private val _uiState = MutableStateFlow<ItineraryUiState>(ItineraryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getItineraryItemList(tripId: Int) = viewModelScope.launch(dispatcher) {
        getItineraryItemListUseCase(tripId).onSuccess { itineraryItemList ->
            _uiState.update {
                if (itineraryItemList.isEmpty()) {
                    ItineraryUiState.Empty
                } else {
                    ItineraryUiState.Content(itineraryItemList)
                }
            }
        }
    }
}