package com.luisfagundes.trip.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.provider.TripDetailsPreviewParameterProvider
import com.luisfagundes.trip.presentation.state.TripDetailsUiState
import com.luisfagundes.trip.presentation.viewmodel.TripDetailsViewModel
import com.luisfagundes.trip.tools.extensions.capitalize
import com.luisfagundes.trip.tools.extensions.formatTripPeriod
import com.luisfagundes.trip.tools.extensions.getTripDurationInDays

@Composable
internal fun TripDetailsScreen(
    tripId: Int,
    onBackClick: () -> Unit,
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTripById(id = tripId)
    }

    when (val state = uiState) {
        is TripDetailsUiState.Loading -> TripDetailsLoadingContent(
            modifier = Modifier.fillMaxSize()
        )

        is TripDetailsUiState.Error -> TripDetailsErrorContent(
            message = state.message
        )

        is TripDetailsUiState.Success -> TripDetailsContent(
            trip = state.trip,
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
private fun TripDetailsContent(
    trip: Trip,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Text(
                text = trip.status.name.capitalize(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(MaterialTheme.spacing.default)
                    )
                    .padding(horizontal = MaterialTheme.spacing.small)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
                    .fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                )
                Spacer(
                    modifier = Modifier.width(MaterialTheme.spacing.small)
                )
                Text(
                    text = formatTripPeriod(trip.startDate, trip.endDate)
                )
                Text(
                    text = stringResource(R.string.dot),
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                )
                Text(
                    text = pluralStringResource(
                        id = R.plurals.trip_duration_days,
                        count = getTripDurationInDays(trip.startDate, trip.endDate),
                        getTripDurationInDays(trip.startDate, trip.endDate)
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@RedknotPreview
@Composable
private fun TripDetailsPreviewScreen(
    @PreviewParameter(TripDetailsPreviewParameterProvider::class)
    uiState: TripDetailsUiState.Success
) {
    RedknotThemePreview {
        TripDetailsContent(
            trip = uiState.trip,
            onBackClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}