package com.luisfagundes.trip.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.luisfagundes.designsystem.components.LottieAnimationLoader
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.provider.TripListPreviewParameterProvider
import com.luisfagundes.trip.presentation.state.TripListUiState
import com.luisfagundes.trip.presentation.tools.formatTripPeriod
import com.luisfagundes.trip.presentation.viewmodel.TripListViewModel

@Composable
internal fun TripListScreen(
    viewModel: TripListViewModel = hiltViewModel(),
    onTripClick: (Trip) -> Unit,
    onTripCreationClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is TripListUiState.Loading -> TripListLoadingContent(
            modifier = Modifier.fillMaxSize()
        )

        is TripListUiState.Empty -> TripListEmptyContent(
            onTripCreationClick = onTripCreationClick,
            modifier = Modifier
                .padding(MaterialTheme.spacing.default)
                .fillMaxSize()
        )

        is TripListUiState.Content -> TripListContent(
            upcomingTrips = state.upcomingTrips,
            pastTrips = state.pastTrips,
            onTripClick = onTripClick,
            modifier = Modifier.fillMaxWidth()
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
    onTripCreationClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimationLoader(
            animationResId = R.raw.bird_flapping_animation
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.default)
        )
        Text(
            text = stringResource(R.string.no_trips_found_description),
            textAlign = TextAlign.Center,
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.default)
        )
        Button(
            onClick = onTripCreationClick,
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Default.Add),
                contentDescription = null
            )
            Spacer(Modifier.width(MaterialTheme.spacing.small))
            Text(
                text = stringResource(R.string.create_new_trip)
            )
        }
    }
}

@Composable
private fun TripListContent(
    upcomingTrips: List<Trip>,
    pastTrips: List<Trip>,
    onTripClick: (Trip) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        tripSection(
            titleResId = R.string.upcoming,
            trips = upcomingTrips,
            onTripClick = onTripClick
        )
        tripSection(
            titleResId = R.string.past,
            trips = pastTrips,
            onTripClick = onTripClick
        )
    }
}

private fun LazyListScope.tripSection(
    @StringRes titleResId: Int,
    trips: List<Trip>,
    onTripClick: (Trip) -> Unit
) {
    if (trips.isEmpty()) {
        return
    }
    item {
        Text(
            text = stringResource(titleResId),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
    items(
        items = trips,
        key = { it.id }
    ) { trip ->
        TripContent(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTripClick(trip) },
            trip = trip,
        )
    }
}

@Composable
private fun TripContent(
    trip: Trip,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(trip.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = trip.location
        )
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.default)
        ) {
            Text(
                text = formatTripPeriod(trip.startDate, trip.endDate),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = trip.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = trip.location
            )
        }
    }
}

@RedknotPreview
@Composable
private fun TripListContentPreview(
    @PreviewParameter(TripListPreviewParameterProvider::class)
    uiState: TripListUiState.Content
) {
    RedknotThemePreview {
        TripListContent(
            upcomingTrips = uiState.upcomingTrips,
            pastTrips = uiState.pastTrips,
            onTripClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}