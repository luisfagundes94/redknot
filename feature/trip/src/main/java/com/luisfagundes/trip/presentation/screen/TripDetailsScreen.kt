package com.luisfagundes.trip.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.presentation.screen.ItineraryScreen
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.components.details.DeleteTripConfirmationDialog
import com.luisfagundes.trip.presentation.components.details.TripDetailsHeader
import com.luisfagundes.trip.presentation.components.details.TripDetailsTabRow
import com.luisfagundes.trip.presentation.model.TripDetailsTabs
import com.luisfagundes.trip.presentation.provider.TripDetailsPreviewParameterProvider
import com.luisfagundes.trip.presentation.viewmodel.state.TripDetailsUiState
import com.luisfagundes.trip.presentation.viewmodel.TripDetailsViewModel
import com.luisfagundes.trip.presentation.viewmodel.effect.TripDetailsUiEffect

@Composable
internal fun TripDetailsScreen(
    tripId: Int,
    onNewItineraryItemClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentOnBackClick by rememberUpdatedState(onBackClick)

    LaunchedEffect(Unit) {
        viewModel.getTripById(id = tripId)
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is TripDetailsUiEffect.NavigateBack -> currentOnBackClick()
            is TripDetailsUiEffect.ShowErrorToast -> {
                Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    TripDetailsContent(
        uiState = uiState,
        onNewItineraryItemClick = onNewItineraryItemClick,
        onDeleteClick = { viewModel.deleteTrip(tripId) },
        onBackClick = onBackClick
    )
}

@Composable
private fun TripDetailsContent(
    uiState: TripDetailsUiState,
    onNewItineraryItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    when (uiState) {
        is TripDetailsUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )

        is TripDetailsUiState.Error -> TripDetailsErrorContent(
            message = uiState.message ?: stringResource(R.string.generic_error_message)
        )

        is TripDetailsUiState.Success -> TripDetailsSuccessContent(
            trip = uiState.trip,
            itineraryContent = {
                ItineraryScreen(
                    tripId = uiState.trip.id,
                    onEffect = { effect ->
                        when (effect) {
                            is ItineraryUiEffect.NavigateToItineraryItemForm -> onNewItineraryItemClick()
                        }
                    }
                )
            },
            onDeleteClick = onDeleteClick,
            onBackClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        )
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
    itineraryContent: @Composable () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentSelectedTab by rememberSaveable { mutableStateOf(TripDetailsTabs.ITINERARY) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteTripConfirmationDialog(
            onDismissRequest = { showDeleteDialog = false },
            onDeleteClick = { showDeleteDialog = false; onDeleteClick() }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RedknotTopBar(
                title = trip.title,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_trip)
                        )
                    }
                }
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
                TripDetailsTabs.ITINERARY -> itineraryContent()
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
            itineraryContent = {},
            onDeleteClick = {},
            onBackClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
