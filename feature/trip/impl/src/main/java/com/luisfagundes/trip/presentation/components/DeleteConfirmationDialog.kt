package com.luisfagundes.trip.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.luisfagundes.trip.R

@Composable
internal fun DeleteConfirmationDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDeleteClick) {
                Text(stringResource(R.string.delete_trip_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.delete_trip_dialog_cancel))
            }
        }
    )
}
