package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.repository.TripRepository
import com.luisfagundes.trip.presentation.state.TripListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripListViewModel @Inject constructor(
    private val repository: TripRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _uiState = MutableStateFlow<TripListUiState>(TripListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getTripList()
    }

    fun getTripList() = viewModelScope.launch(dispatcher) {
        repository.getTripList().fold(
            onSuccess = { trips ->
                val upcomingTrips = trips.filter { !it.done }
                val pastTrips = trips.filter { it.done }

                if (upcomingTrips.isEmpty() && pastTrips.isEmpty()) {
                    _uiState.update { TripListUiState.Empty }
                    return@launch
                }

                _uiState.update {
                    TripListUiState.Content(
                        upcomingTrips = upcomingTrips,
                        pastTrips = pastTrips
                    )
                }
            },
            onFailure = {}
        )
    }
}