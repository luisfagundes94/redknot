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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.luisfagundes.trip.domain.model.TripSection
import com.luisfagundes.trip.presentation.mapper.toTitleResId
import com.luisfagundes.trip.presentation.provider.TripListPreviewParameterProvider
import com.luisfagundes.trip.presentation.state.TripListUiState
import com.luisfagundes.trip.tools.extensions.formatTripPeriod
import com.luisfagundes.trip.presentation.viewmodel.TripListViewModel

@Composable
internal fun TripListScreen(
    onTripClick: (Trip) -> Unit,
    onCreateTripClick: () -> Unit,
    viewModel: TripListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTripList()
    }

    when (val state = uiState) {
        is TripListUiState.Loading -> TripListLoadingContent(
            modifier = Modifier.fillMaxSize()
        )

        is TripListUiState.Empty -> TripListEmptyContent(
            onTripCreationClick = onCreateTripClick,
            modifier = Modifier
                .padding(MaterialTheme.spacing.default)
                .fillMaxSize()
        )

        is TripListUiState.Error -> TripListErrorContent(
            onTryAgainClick = { viewModel.getTripList() },
            modifier = Modifier.fillMaxSize()
        )

        is TripListUiState.Content -> TripListContent(
            tripSectionList = state.tripSectionList,
            onTripClick = onTripClick,
            onCreateTripClick = onCreateTripClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TripListLoadingContent(
    modifier: Modifier = Modifier
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
    modifier: Modifier = Modifier
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
private fun TripListErrorContent(
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error_occurred_try_again),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onTryAgainClick
        ) {
            Text(
                text = stringResource(R.string.try_again)
            )
        }
    }
}

@Composable
private fun TripListContent(
    tripSectionList: List<TripSection>,
    onTripClick: (Trip) -> Unit,
    onCreateTripClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Button(
                onClick = onCreateTripClick
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Add),
                    contentDescription = stringResource(R.string.create_new_trip)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            tripSectionList.forEach { tripSection ->
                tripSection(
                    titleResId = tripSection.type.toTitleResId(),
                    trips = tripSection.trips,
                    onTripClick = onTripClick
                )
            }
        }
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
                .clickable { onTripClick(trip) }
                .animateItem(),
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
            tripSectionList = uiState.tripSectionList,
            onTripClick = {},
            onCreateTripClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}