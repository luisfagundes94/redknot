package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.luisfagundes.trip.presentation.state.TripCreationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class TripCreationViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(TripCreationUiState())
    val uiState = _uiState.asStateFlow()

}