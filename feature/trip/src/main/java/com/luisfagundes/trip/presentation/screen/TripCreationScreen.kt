package com.luisfagundes.trip.presentation.screen

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import com.luisfagundes.trip.extensions.convertMillisToLocalDate
import com.luisfagundes.trip.extensions.toFormattedString
import com.luisfagundes.trip.presentation.state.TripCreationUiState
import com.luisfagundes.trip.presentation.viewmodel.TripCreationViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TripCreationScreen(
    viewModel: TripCreationViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TripCreationContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onDestinationChange = viewModel::onDestinationChange,
        onStartDateChange = viewModel::onStartDateChange,
        onEndDateChange = viewModel::onEndDateChange,
        onSubmitForm = viewModel::onSubmit,
        onBackClick = onBackClick,
    )
}

@Composable
private fun TripCreationContent(
    uiState: TripCreationUiState,
    onNameChange: (String) -> Unit,
    onDestinationChange: (String) -> Unit,
    onStartDateChange: (LocalDate?) -> Unit,
    onEndDateChange: (LocalDate?) -> Unit,
    onSubmitForm: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_new_trip),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default),
            modifier = Modifier
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.default)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.trip_name_label)) },
                placeholder = { Text(stringResource(R.string.trip_name_placeholder)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.destination,
                onValueChange = onDestinationChange,
                label = { Text(stringResource(R.string.trip_destination_label)) },
                placeholder = { Text(stringResource(R.string.trip_destination_placeholder)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            DateSelectionField(
                value = uiState.startDate,
                label = stringResource(R.string.start_date_label),
                placeholder = stringResource(R.string.start_date_placeholder),
                onDateSelected = onStartDateChange,
                modifier = Modifier.fillMaxWidth()
            )
            DateSelectionField(
                value = uiState.endDate,
                label = stringResource(R.string.end_date_label),
                placeholder = stringResource(R.string.end_date_placeholder),
                onDateSelected = onEndDateChange,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onSubmitForm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.create_trip)
                    )
                }
            }
        }
    }
}


@Composable
private fun DateSelectionField(
    value: LocalDate?,
    label: String,
    placeholder: String,
    onDateSelected: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value.toFormattedString(),
        onValueChange = { },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.select_date_icon_description)
            )
        },
        readOnly = true,
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial).consume()
                    showDatePicker = true
                }
            }
    )

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = onDateSelected,
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
fun DatePickerModal(
    onDateSelected: (LocalDate?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis.convertMillisToLocalDate())
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@RedknotPreview
@Composable
private fun TripCreationScreenPreview() {
    RedknotThemePreview {
        TripCreationContent(
            uiState = TripCreationUiState(),
            onNameChange = {},
            onDestinationChange = {},
            onStartDateChange = {},
            onEndDateChange = {},
            onSubmitForm = {},
            onBackClick = {}
        )
    }
}
