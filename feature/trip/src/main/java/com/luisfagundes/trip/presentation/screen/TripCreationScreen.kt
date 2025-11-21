package com.luisfagundes.trip.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.trip.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TripCreationScreen(
    onBackClick: () -> Unit
) {
    Column {
        RedknotTopBar(
            title = stringResource(R.string.create_new_trip),
            onBackClick = onBackClick
        )
    }
}