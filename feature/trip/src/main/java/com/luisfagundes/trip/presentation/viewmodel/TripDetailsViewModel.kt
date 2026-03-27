package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.core.presentation.arch.StateViewModel
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.trip.presentation.viewmodel.event.TripDetailsUiEvent
import com.luisfagundes.trip.presentation.viewmodel.state.TripDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripDetailsViewModel @Inject constructor(
    private val getTripByIdUseCase: GetTripByIdUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : StateViewModel<TripDetailsUiState, TripDetailsUiEvent>() {
    override fun initialState() = TripDetailsUiState.Loading

    override fun dispatchEvent(event: TripDetailsUiEvent) {
        when (event) {
            is TripDetailsUiEvent.OnGetTripById -> getTripById(event.id)
        }
    }

    private fun getTripById(id: Int) = viewModelScope.launch(dispatcher) {
        setState { TripDetailsUiState.Loading }

        getTripByIdUseCase.invoke(id).fold(
            onSuccess = { trip ->
                setState { TripDetailsUiState.Success(trip) }
            },
            onFailure = { throwable ->
                setState { TripDetailsUiState.Error(throwable.message) }
            }
        )
    }
}