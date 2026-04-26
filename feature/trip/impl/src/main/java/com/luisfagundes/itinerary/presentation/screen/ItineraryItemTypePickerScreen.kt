package com.luisfagundes.itinerary.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R

@Composable
internal fun ItineraryItemTypePickerScreen(
    onActivityClick: () -> Unit,
    onAccommodationClick: () -> Unit,
    onFlightClick: () -> Unit,
    onRestaurantClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.pick_itinerary_item_type),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.spacing.default)
                .fillMaxSize()
        ) {
            item {
                ItineraryTypeCard(
                    label = stringResource(R.string.itinerary_item_type_activity),
                    onClick = onActivityClick
                )
            }
            item {
                ItineraryTypeCard(
                    label = stringResource(R.string.itinerary_item_type_accommodation),
                    onClick = onAccommodationClick
                )
            }
            item {
                ItineraryTypeCard(
                    label = stringResource(R.string.itinerary_item_type_flight),
                    onClick = onFlightClick
                )
            }
            item {
                ItineraryTypeCard(
                    label = stringResource(R.string.itinerary_item_type_restaurant),
                    onClick = onRestaurantClick
                )
            }
        }
    }
}

@Composable
private fun ItineraryTypeCard(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.spacing.small)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.default)) {
            Text(text = label, style = MaterialTheme.typography.titleMedium)
        }
    }
}
