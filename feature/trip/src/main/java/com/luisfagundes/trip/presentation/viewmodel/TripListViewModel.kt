package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.usecase.GetTripListUseCase
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
    private val getTripListUseCase: GetTripListUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _uiState = MutableStateFlow<TripListUiState>(TripListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getTripList() = viewModelScope.launch(dispatcher) {
        _uiState.update { TripListUiState.Loading }

        getTripListUseCase.invoke().fold(
            onSuccess = { tripsByStatus ->
                if (tripsByStatus.isEmpty()) {
                    _uiState.update { TripListUiState.Empty }
                    return@fold
                }
                _uiState.update {
                    TripListUiState.Success(tripsByStatus)
                }
            },
            onFailure = {
                _uiState.update { TripListUiState.Error }
            }
        )
    }
}