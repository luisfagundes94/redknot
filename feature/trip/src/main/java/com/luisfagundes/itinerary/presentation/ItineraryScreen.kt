package com.luisfagundes.itinerary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R

@Composable
internal fun ItineraryScreen(
    tripId: Int,
    onNewItineraryItemClick: () -> Unit,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getItineraryItemList(tripId)
    }

    when (val state = uiState) {
        ItineraryUiState.Loading -> ItineraryLoadingContent(
            modifier = Modifier.fillMaxSize()
        )
        ItineraryUiState.Empty -> ItineraryEmptyContent(
            onNewItineraryItemClick = onNewItineraryItemClick,
            modifier = Modifier
                .padding(MaterialTheme.spacing.default)
                .fillMaxSize()
        )
        is ItineraryUiState.Content -> Unit
    }
}

@Composable
private fun ItineraryLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ItineraryEmptyContent(
    onNewItineraryItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.empty_itinerary_message),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.default)
        )
        Button(
            onClick = onNewItineraryItemClick
        ) {
            Text(
                text = stringResource(R.string.add_itinerary_item)
            )
        }
    }
}