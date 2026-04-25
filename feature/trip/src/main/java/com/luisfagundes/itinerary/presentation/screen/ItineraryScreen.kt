package com.luisfagundes.itinerary.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotEmptyTemplate
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.presentation.provider.ItineraryPreviewParameterProvider
import com.luisfagundes.itinerary.presentation.components.ItineraryTimeline
import com.luisfagundes.itinerary.presentation.viewmodel.ItineraryViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import com.luisfagundes.trip.R

@Composable
internal fun ItineraryScreen(
    tripId: Int,
    onEffect: (ItineraryUiEffect) -> Unit,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getItineraryList(tripId)
    }

    CollectUiEffects(
        flow = viewModel.uiEffect,
        onEffect = onEffect
    )

    ItineraryContent(
        uiState = uiState,
        onAddItem = viewModel::onAddItineraryItem,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ItineraryContent(
    uiState: ItineraryUiState,
    onAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        ItineraryUiState.Loading -> RedknotLoadingTemplate(
            modifier = modifier
        )

        ItineraryUiState.Empty -> RedknotEmptyTemplate(
            title = stringResource(R.string.empty_itinerary_message),
            primaryButtonLabel = stringResource(R.string.add_itinerary_item),
            onPrimaryButtonClick = onAddItem,
            modifier = Modifier
                .padding(MaterialTheme.spacing.default)
                .fillMaxSize()
        )

        is ItineraryUiState.Content -> ItineraryTimelineContent(
            items = uiState.items,
            onAddItem = onAddItem,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItineraryTimelineContent(
    items: List<ItineraryItem>,
    onAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddItem,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add event"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(MaterialTheme.spacing.default)
        ) {
            itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
                ItineraryTimeline(
                    item = item,
                    isFirst = index == 0,
                    isLast = index == items.lastIndex
                )
            }
        }
    }
}

@RedknotPreview
@Composable
private fun ItineraryContentPreview(
    @PreviewParameter(ItineraryPreviewParameterProvider::class)
    uiState: ItineraryUiState.Content
) {
    RedknotThemePreview {
        ItineraryTimelineContent(
            items = uiState.items,
            onAddItem = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

