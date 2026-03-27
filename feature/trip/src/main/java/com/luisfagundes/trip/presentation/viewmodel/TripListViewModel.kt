package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.presentation.arch.ViewModel
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.usecase.GetTripListUseCase
import com.luisfagundes.trip.presentation.viewmodel.action.TripListUiAction
import com.luisfagundes.trip.presentation.viewmodel.event.TripListUiEvent
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
) : ViewModel<TripListUiState, TripListUiEvent, TripListUiAction>() {
    override fun initialState() = TripListUiState.Loading

    override fun dispatchEvent(event: TripListUiEvent) {
        when (event) {
            is TripListUiEvent.OnGetTripList -> getTripList()
            is TripListUiEvent.OnCreateTripClick -> onCreateTripClick()
            is TripListUiEvent.OnTripClick -> onTripClick(event.id)
            is TripListUiEvent.OnTryAgainClick -> getTripList()
        }
    }

    private fun getTripList() = viewModelScope.launch(dispatcher) {
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

    private fun onCreateTripClick() {
        sendAction { TripListUiAction.NavigateToTripForm }
    }

    private fun onTripClick(id: Int) {
        sendAction { TripListUiAction.NavigateToTripDetails(id = id) }
    }
}