package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.state.TripListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripListViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<TripListUiState>(TripListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getTripList()
    }

    fun getTripList() = viewModelScope.launch {
        val trips = listOf(
            Trip(
                imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
                title = "Summer in Italy",
                period = "Aug 15 - Aug 25, 2026",
                location = "Florence, Italy"
            ),
            Trip(
                imageUrl = "https://images.pexels.com/photos/2082103/pexels-photo-2082103.jpeg",
                title = "Weekend in Paris",
                period = "Sep 20 - Sep 24, 2026",
                location = "Paris, France"
            ),
        )
        _uiState.update { TripListUiState.Content(trips = trips) }
    }
}