package com.luisfagundes.trip.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.provider.TripListPreviewParameterProvider
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
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        items(trips) { trip ->
            Card(
                modifier = Modifier.fillMaxWidth()
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
                        text = trip.period,
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
            modifier = Modifier.fillMaxWidth(),
            trips = uiState.trips
        )
    }
}