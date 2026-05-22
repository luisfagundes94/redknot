package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.trip.domain.usecase.GetTripListUseCase
import com.luisfagundes.trip.presentation.viewmodel.effect.TripListUiEffect
import com.luisfagundes.trip.presentation.viewmodel.state.TripListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripListViewModel @Inject constructor(
    private val getTripListUseCase: GetTripListUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<TripListUiState, TripListUiEffect>(
    initialState = TripListUiState.Loading
) {
    fun getTripList() = viewModelScope.launch(dispatcher) {
        setState { TripListUiState.Loading }

        getTripListUseCase.invoke().fold(
            onSuccess = { tripsByStatus ->
                if (tripsByStatus.isEmpty()) {
                    setState { TripListUiState.Empty }
                    return@fold
                }
                setState {
                    TripListUiState.Success(tripsByStatus)
                }
            },
            onFailure = {
                setState { TripListUiState.Error }
            }
        )
    }

    fun navigateToTripForm() {
        sendEffect { TripListUiEffect.NavigateToTripForm }
    }

    fun navigateToTripDetails(id: Int) {
        sendEffect { TripListUiEffect.NavigateToTripDetails(id = id) }
    }
}