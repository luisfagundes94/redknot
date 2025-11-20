package com.luisfagundes.trip.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.state.TripListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TripListViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<TripListUiState>(TripListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getTripList()
    }

    fun getTripList() = viewModelScope.launch {
        val trips = listOf(
            Trip(
                imageUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.britannica.com%2F71%2F8671-050-2EE6A745%2FCathedral-Florence-Santa-Maria-del-Fiore.jpg&f=1&nofb=1&ipt=2d75884d70593fcc984684df2be41864ab34415a5a134c48af10e45c175da8b0",
                title = "Summer in Italy",
                period = "Aug 15 - Aug 25, 2026",
                location = "Florence, Italy"
            ),
            Trip(
                imageUrl = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-I5uqqTIiGT8%2FT25VtD1SYGI%2FAAAAAAAAEoU%2FwQzqFsqrt50%2Fs1600%2Ffrance-paris%2B31.jpg&f=1&nofb=1&ipt=8008a9618330692b80e446be833ac25012e006b66a430b87d61cd24864b64af7",
                title = "Weekend in Paris",
                period = "Sep 20 - Sep 24, 2026",
                location = "Paris, France"
            ),
        )
        _uiState.update { TripListUiState.Content(trips = trips) }
    }
}