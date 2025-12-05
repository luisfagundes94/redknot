package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.di.IoDispatcher
import com.luisfagundes.trip.domain.usecase.CreateTripUseCase
import com.luisfagundes.trip.presentation.state.TripCreationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class TripCreationViewModel @Inject constructor(
    private val createTripUseCase: CreateTripUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(TripCreationUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onStartDateChange(date: LocalDate?) {
        _uiState.update { it.copy(startDate = date) }
    }

    fun onEndDateChange(date: LocalDate?) {
        _uiState.update { it.copy(endDate = date) }
    }

    fun onDestinationChange(destination: String) {
        _uiState.update { it.copy(destination = destination) }
    }

    fun onSubmit() {
        if (_uiState.value.isFormValid()) {
            viewModelScope.launch(dispatcher) {
                createTripUseCase.invoke()
            }
        }
    }
}