package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.trip.domain.usecase.DeleteTripUseCase
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.trip.presentation.viewmodel.effect.TripDetailsUiEffect
import com.luisfagundes.trip.presentation.viewmodel.event.TripDetailsUiEvent
import com.luisfagundes.trip.presentation.viewmodel.state.TripDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripDetailsViewModel @Inject constructor(
    private val getTripByIdUseCase: GetTripByIdUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<TripDetailsUiState, TripDetailsUiEvent, TripDetailsUiEffect>(
    initialState = TripDetailsUiState.Loading
) {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    override fun dispatchEvent(event: TripDetailsUiEvent) {
        when (event) {
            is TripDetailsUiEvent.GetTripById -> getTripById(event.id)
            is TripDetailsUiEvent.DeleteTrip -> deleteTrip(event.id)
            is TripDetailsUiEvent.SelectTab -> selectTab(event.index)
        }
    }

    private fun selectTab(index: Int) {
        _selectedTabIndex.value = index
    }

    private fun getTripById(id: Int) = viewModelScope.launch(dispatcher) {
        setState { TripDetailsUiState.Loading }

        getTripByIdUseCase.invoke(id).fold(
            onSuccess = { trip -> setState { TripDetailsUiState.Success(trip) } },
            onFailure = { throwable -> setState { TripDetailsUiState.Error(throwable.message) } }
        )
    }

    private fun deleteTrip(id: Int) = viewModelScope.launch(dispatcher) {
        deleteTripUseCase(id).fold(
            onSuccess = { sendEffect { TripDetailsUiEffect.NavigateBack } },
            onFailure = { sendEffect { TripDetailsUiEffect.ShowErrorToast(it.toString()) } }
        )
    }
}
