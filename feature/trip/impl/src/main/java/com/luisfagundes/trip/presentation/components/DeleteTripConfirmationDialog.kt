package com.luisfagundes.trip.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.luisfagundes.trip.R

@Composable
internal fun DeleteTripConfirmationDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit
) {
    DeleteConfirmationDialog(
        title = stringResource(R.string.delete_trip_dialog_title),
        message = stringResource(R.string.delete_trip_dialog_message),
        onDismissRequest = onDismissRequest,
        onDeleteClick = onDeleteClick
    )
}
