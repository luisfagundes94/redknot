package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.presentation.arch.ViewModel
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.trip.presentation.viewmodel.action.TripDetailsUiAction
import com.luisfagundes.trip.presentation.viewmodel.state.TripDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripDetailsViewModel @Inject constructor(
    private val getTripByIdUseCase: GetTripByIdUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<TripDetailsUiState, TripDetailsUiAction>() {
    override fun initialState() = TripDetailsUiState.Loading

    fun getTripById(id: Int) = viewModelScope.launch(dispatcher) {
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