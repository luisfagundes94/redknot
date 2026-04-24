package com.luisfagundes.itinerary.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.presentation.arch.compose.CollectUiActions
import com.luisfagundes.designsystem.components.RedknotEmptyTemplate
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import com.luisfagundes.itinerary.presentation.viewmodel.ItineraryViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.action.ItineraryUiAction
import com.luisfagundes.itinerary.presentation.viewmodel.event.ItineraryUiEvent
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.viewmodel.event.TripDetailsUiEvent

@Composable
internal fun ItineraryScreen(
    tripId: Int,
    onAction: (ItineraryUiAction) -> Unit,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getItineraryList(tripId)
    }

    CollectUiActions(
        flow = viewModel.uiAction,
        onAction = onAction
    )

    ItineraryContent(
        uiState = uiState,
        onNewItineraryItemClick = viewModel::onNewItineraryItemClick,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ItineraryContent(
    uiState: ItineraryUiState,
    onNewItineraryItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        ItineraryUiState.Loading -> RedknotLoadingTemplate(
            modifier = modifier
        )
        ItineraryUiState.Empty -> RedknotEmptyTemplate(
            title = stringResource(R.string.empty_itinerary_message),
            primaryButtonLabel = stringResource(R.string.add_itinerary_item),
            onPrimaryButtonClick = onNewItineraryItemClick,
            modifier = Modifier
                .padding(MaterialTheme.spacing.default)
                .fillMaxSize()
        )
        is ItineraryUiState.Content -> Unit
    }
}