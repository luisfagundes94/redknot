package com.luisfagundes.trip.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.RedknotPreview
import com.luisfagundes.designsystem.theme.RedknotThemePreview
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.effect.TripFormUiEffect
import com.luisfagundes.trip.presentation.mapper.toErrorMessage
import com.luisfagundes.trip.presentation.state.TripFormUiState
import com.luisfagundes.trip.presentation.viewmodel.TripFormViewModel
import com.luisfagundes.trip.tools.extensions.capitalizeEveryWord
import com.luisfagundes.trip.tools.extensions.convertMillisToLocalDate
import com.luisfagundes.trip.tools.extensions.toFormattedString
import com.luisfagundes.trip.tools.extensions.toTimestampMillis
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TripFormScreen(
    viewModel: TripFormViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentOnBackClick by rememberUpdatedState(onBackClick)

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is TripFormUiEffect.NavigateBack -> {
                    currentOnBackClick()
                }

                is TripFormUiEffect.ShowErrorToast -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    TripFormContent(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onDestinationChange = viewModel::onDestinationChange,
        onStartDateChange = viewModel::onStartDateChange,
        onEndDateChange = viewModel::onEndDateChange,
        onSubmitForm = viewModel::onSubmit,
        onBackClick = onBackClick,
    )
}

@Composable
private fun TripFormContent(
    uiState: TripFormUiState,
    onTitleChange: (String) -> Unit,
    onDestinationChange: (String) -> Unit,
    onStartDateChange: (LocalDate?) -> Unit,
    onEndDateChange: (LocalDate?) -> Unit,
    onSubmitForm: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_new_trip),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.spacing.default)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { onTitleChange.invoke(it.capitalizeEveryWord()) },
                label = { Text(stringResource(R.string.trip_name_label)) },
                placeholder = { Text(stringResource(R.string.trip_name_placeholder)) },
                singleLine = true,
                isError = uiState.titleError != null,
                supportingText = {
                    uiState.titleError?.let {
                        Text(it.toErrorMessage(context))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.destination,
                onValueChange = { onDestinationChange.invoke(it.capitalizeEveryWord()) },
                label = { Text(stringResource(R.string.trip_destination_label)) },
                placeholder = { Text(stringResource(R.string.trip_destination_placeholder)) },
                singleLine = true,
                isError = uiState.destinationError != null,
                supportingText = {
                    uiState.destinationError?.let {
                        Text(it.toErrorMessage(context))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            DateSelectionField(
                value = uiState.startDate,
                label = stringResource(R.string.start_date_label),
                placeholder = stringResource(R.string.start_date_placeholder),
                hasError = uiState.startDateError != null,
                supportingText = {
                    uiState.startDateError?.let { error ->
                        Text(error.toErrorMessage(context))
                    }
                },
                onDateSelect = onStartDateChange,
                modifier = Modifier.fillMaxWidth()
            )
            DateSelectionField(
                value = uiState.endDate,
                label = stringResource(R.string.end_date_label),
                placeholder = stringResource(R.string.end_date_placeholder),
                hasError = uiState.endDateError != null,
                supportingText = {
                    uiState.endDateError?.let { error ->
                        Text(error.toErrorMessage(context))
                    }
                },
                onDateSelect = onEndDateChange,
                modifier = Modifier.fillMaxWidth(),
                startDate = uiState.startDate
            )
            CreateTripButton(
                uiState = uiState,
                onSubmitForm = onSubmitForm
            )
        }
    }
}

@Composable
private fun DateSelectionField(
    value: LocalDate?,
    label: String,
    placeholder: String,
    hasError: Boolean,
    onDateSelect: (LocalDate?) -> Unit,
    supportingText: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    startDate: LocalDate? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dismissPicker = remember { { showDatePicker = false } }

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
        isError = hasError,
        supportingText = supportingText,
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
            onDateSelect = onDateSelect,
            onDismiss = dismissPicker,
            startDate = startDate
        )
    }
}

@Composable
fun DatePickerModal(
    onDateSelect: (LocalDate?) -> Unit,
    onDismiss: () -> Unit,
    startDate: LocalDate? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = startDate?.toTimestampMillis()
    )

    DatePickerDialog(
        confirmButton = {
            TextButton(onClick = {
                onDateSelect(datePickerState.selectedDateMillis.convertMillisToLocalDate())
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        onDismissRequest = onDismiss
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun CreateTripButton(
    uiState: TripFormUiState,
    onSubmitForm: () -> Unit
) {
    Button(
        onClick = onSubmitForm,
        enabled = uiState.isFormValid && !uiState.isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.default)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
                Text(
                    text = stringResource(
                        id = R.string.create_trip
                    )
                )
            }
        }
    }
}

@RedknotPreview
@Composable
private fun TripFormScreenPreview() {
    RedknotThemePreview {
        TripFormContent(
            uiState = TripFormUiState(),
            onTitleChange = {},
            onDestinationChange = {},
            onStartDateChange = {},
            onEndDateChange = {},
            onSubmitForm = {},
            onBackClick = {}
        )
    }
}
