package com.luisfagundes.itinerary.presentation.screen

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
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotEmptyTemplate
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemeWrapper
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.presentation.provider.ItineraryPreviewParameterProvider
import com.luisfagundes.itinerary.presentation.components.ItineraryDayHeader
import com.luisfagundes.itinerary.presentation.components.ItineraryTimeline
import java.time.LocalDate
import com.luisfagundes.itinerary.presentation.viewmodel.ItineraryViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import com.luisfagundes.trip.R

@Composable
internal fun ItineraryScreen(
    tripId: Int,
    onAddItineraryItemClick: () -> Unit,
    onEditItineraryItemClick: (ItineraryItem) -> Unit,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getItineraryItemsByDay(tripId)
    }

    CollectUiEffects(
        flow = viewModel.uiEffect,
        onEffect = { effect ->
            when (effect) {
                is ItineraryUiEffect.NavigateToItineraryItemForm -> onAddItineraryItemClick()
                is ItineraryUiEffect.NavigateToEditItineraryItem -> onEditItineraryItemClick(effect.item)
            }
        }
    )

    ItineraryContent(
        uiState = uiState,
        onAddItem = viewModel::onAddItineraryItem,
        onItemClick = viewModel::onItineraryItemClick,
    )
}

@Composable
private fun ItineraryContent(
    uiState: ItineraryUiState,
    onAddItem: () -> Unit,
    onItemClick: (ItineraryItem) -> Unit,
) {
    when (uiState) {
        ItineraryUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
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
            itemsByDay = uiState.itemsByDay,
            onAddItem = onAddItem,
            onItemClick = onItemClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItineraryTimelineContent(
    itemsByDay: Map<LocalDate, List<ItineraryItem>>,
    onAddItem: () -> Unit,
    onItemClick: (ItineraryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddItem
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add event"
                )
            }
        }
    ) { internalPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(internalPadding)
                .fillMaxWidth(),
            contentPadding = PaddingValues(MaterialTheme.spacing.default)
        ) {
            itemsByDay.forEach { (date, dayItems) ->
                item(key = date) {
                    ItineraryDayHeader(
                        date = date,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                    )
                }
                itemsIndexed(dayItems, key = { _, item -> item.id }) { index, item ->
                    ItineraryTimeline(
                        item = item,
                        isFirst = index == 0,
                        isLast = index == dayItems.lastIndex,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@PreviewWrapper(RedknotThemeWrapper::class)
@RedknotPreview
@Composable
private fun ItineraryContentPreview(
    @PreviewParameter(ItineraryPreviewParameterProvider::class)
    uiState: ItineraryUiState.Content
) {
    ItineraryTimelineContent(
        itemsByDay = uiState.itemsByDay,
        onAddItem = {},
        onItemClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}
