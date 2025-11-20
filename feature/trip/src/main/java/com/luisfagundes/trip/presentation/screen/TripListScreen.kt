package com.luisfagundes.trip.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.state.TripListUiState
import com.luisfagundes.trip.presentation.viewmodel.TripListViewModel

@Composable
internal fun TripListScreen(
    viewModel: TripListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is TripListUiState.Loading -> TripListLoadingContent(
            modifier = Modifier.fillMaxSize()
        )

        is TripListUiState.Empty -> TripListEmptyContent(
            modifier = Modifier.fillMaxSize()
        )

        is TripListUiState.Content -> TripListContent(
            modifier = Modifier.fillMaxWidth(),
            trips = state.trips
        )
    }
}

@Composable
private fun TripListLoadingContent(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TripListEmptyContent(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No trips found")
    }
}

@Composable
private fun TripListContent(
    modifier: Modifier,
    trips: List<Trip>
) {

}