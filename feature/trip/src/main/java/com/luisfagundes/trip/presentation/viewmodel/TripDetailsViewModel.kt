package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.trip.presentation.state.TripDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripDetailsViewModel @Inject constructor(
    private val getTripByIdUseCase: GetTripByIdUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<TripDetailsUiState>(TripDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getTripById(id: Int) = viewModelScope.launch(dispatcher) {
        _uiState.update { TripDetailsUiState.Loading }

        getTripByIdUseCase.invoke(id).fold(
            onSuccess = { trip ->
                _uiState.update { TripDetailsUiState.Success(trip) }
            },
            onFailure = { throwable ->
                _uiState.update { TripDetailsUiState.Error(throwable.message) }
            }
        )
    }
}