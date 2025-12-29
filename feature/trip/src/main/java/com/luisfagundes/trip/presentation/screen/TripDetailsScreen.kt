package com.luisfagundes.trip.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.presentation.ItineraryScreen
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.components.TripDetailsHeader
import com.luisfagundes.trip.presentation.components.TripDetailsTabRow
import com.luisfagundes.trip.presentation.model.TripDetailsTabs
import com.luisfagundes.trip.presentation.provider.TripDetailsPreviewParameterProvider
import com.luisfagundes.trip.presentation.state.TripDetailsUiState
import com.luisfagundes.trip.presentation.viewmodel.TripDetailsViewModel

@Composable
internal fun TripDetailsScreen(
    tripId: Int,
    onNewItineraryItemClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTripById(id = tripId)
    }

    TripDetailsContent(
        uiState = uiState,
        onNewItineraryItemClick = onNewItineraryItemClick,
        onBackClick = onBackClick
    )
}

@Composable
private fun TripDetailsContent(
    uiState: TripDetailsUiState,
    onNewItineraryItemClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    when (uiState) {
        is TripDetailsUiState.Loading -> TripDetailsLoadingContent(
            modifier = Modifier.fillMaxSize()
        )

        is TripDetailsUiState.Error -> TripDetailsErrorContent(
            message = uiState.message
        )

        is TripDetailsUiState.Success -> TripDetailsSuccessContent(
            trip = uiState.trip,
            onNewItineraryItemClick = onNewItineraryItemClick,
            onBackClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TripDetailsLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TripDetailsErrorContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = message
        )
    }
}

@Composable
private fun TripDetailsSuccessContent(
    trip: Trip,
    onNewItineraryItemClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentSelectedTab by rememberSaveable { mutableStateOf(TripDetailsTabs.ITINERARY) }

    Scaffold(
        modifier = modifier,
        topBar = {
            RedknotTopBar(
                title = trip.title,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(MaterialTheme.spacing.default)
                .fillMaxWidth()
        ) {
            TripDetailsHeader(
                status = trip.status,
                startDate = trip.startDate,
                endDate = trip.endDate
            )
            TripDetailsTabRow(
                onTabSelect = { currentSelectedTab = it },
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.default)
                    .fillMaxWidth()
            )
            when (currentSelectedTab) {
                TripDetailsTabs.ITINERARY -> ItineraryScreen(
                    tripId = trip.id,
                    onNewItineraryItemClick = onNewItineraryItemClick
                )

                TripDetailsTabs.BUDGET -> Unit
                TripDetailsTabs.DOCUMENTS -> Unit
            }
        }
    }
}

@RedknotPreview
@Composable
private fun TripDetailsSuccessContentPreview(
    @PreviewParameter(TripDetailsPreviewParameterProvider::class)
    uiState: TripDetailsUiState.Success
) {
    RedknotThemePreview {
        TripDetailsSuccessContent(
            trip = uiState.trip,
            onNewItineraryItemClick = {},
            onBackClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}