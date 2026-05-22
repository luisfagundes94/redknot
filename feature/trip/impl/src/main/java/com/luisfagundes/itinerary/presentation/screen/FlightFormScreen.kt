package com.luisfagundes.itinerary.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.common.presentation.mapper.toMessage
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotDateSelectionField
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.presentation.viewmodel.FlightFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.FlightFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.event.FlightFormUiEvent
import com.luisfagundes.itinerary.presentation.viewmodel.state.FlightFormUiState
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.components.DeleteConfirmationDialog
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun FlightFormScreen(
    tripId: Int,
    itineraryItemId: String? = null,
    onBackClick: () -> Unit,
    onNavigateBackToTripDetails: () -> Unit,
    viewModel: FlightFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(FlightFormUiEvent.InitForm(tripId, itineraryItemId))
    }

    CollectUiEffects(
        flow = viewModel.uiEffect,
        onEffect = { effect ->
            when (effect) {
                is FlightFormUiEffect.NavigateBack -> onBackClick()
                is FlightFormUiEffect.NavigateBackToTripDetails -> onNavigateBackToTripDetails()
                is FlightFormUiEffect.ShowErrorToast -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    FlightFormContent(
        tripId = tripId,
        uiState = uiState,
        onEvent = viewModel::dispatchEvent
    )
}

@Composable
private fun FlightFormContent(
    tripId: Int,
    uiState: FlightFormUiState,
    onEvent: (FlightFormUiEvent) -> Unit
) {
    when (uiState) {
        is FlightFormUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )
        is FlightFormUiState.Content -> FlightForm(
            tripId = tripId,
            uiState = uiState,
            onEvent = onEvent
        )
    }
}

@Composable
private fun FlightForm(
    tripId: Int,
    uiState: FlightFormUiState.Content,
    onEvent: (FlightFormUiEvent) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            title = stringResource(R.string.delete_itinerary_item_dialog_title),
            message = stringResource(R.string.delete_itinerary_item_dialog_message),
            onDismissRequest = { showDeleteDialog = false },
            onDeleteClick = {
                showDeleteDialog = false
                onEvent(FlightFormUiEvent.DeleteFlight)
            }
        )
    }

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_flight),
                onBackClick = { onEvent(FlightFormUiEvent.NavigateBack) },
                actions = {
                    if (uiState.isEditMode) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_itinerary_item)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.default)
        ) {
            OutlinedTextField(
                value = uiState.flightNumber,
                onValueChange = { onEvent(FlightFormUiEvent.UpdateFlightNumber(it)) },
                label = { Text(stringResource(R.string.flight_number_label)) },
                placeholder = { Text(stringResource(R.string.flight_number_placeholder)) },
                singleLine = true,
                isError = uiState.flightNumberError != null,
                supportingText = {
                    uiState.flightNumberError?.let { Text(it.toMessage(context)) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Characters
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.companyName,
                onValueChange = { onEvent(FlightFormUiEvent.UpdateCompanyName(it)) },
                label = { Text(stringResource(R.string.company_name_label)) },
                placeholder = { Text(stringResource(R.string.company_name_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.originAirportCity,
                onValueChange = { onEvent(FlightFormUiEvent.UpdateOrigin(it)) },
                label = { Text(stringResource(R.string.origin_airport_city_label)) },
                placeholder = { Text(stringResource(R.string.origin_airport_city_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.default)
            )
            OutlinedTextField(
                value = uiState.destinationAirportCity,
                onValueChange = { onEvent(FlightFormUiEvent.UpdateDestination(it)) },
                label = { Text(stringResource(R.string.destination_airport_city_label)) },
                placeholder = {
                    Text(stringResource(R.string.destination_airport_city_placeholder))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.default)
            )
            Text(
                text = stringResource(R.string.flight_duration_label),
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.durationHours,
                    onValueChange = { onEvent(FlightFormUiEvent.UpdateDuration(it, uiState.durationMinutes)) },
                    label = { Text(stringResource(R.string.flight_duration_hours_label)) },
                    singleLine = true,
                    isError = uiState.durationError != null,
                    supportingText = {
                        uiState.durationError?.let { Text(it.toMessage(context)) }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.durationMinutes,
                    onValueChange = { onEvent(FlightFormUiEvent.UpdateDuration(uiState.durationHours, it)) },
                    label = { Text(stringResource(R.string.flight_duration_minutes_label)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = uiState.seatNumber,
                onValueChange = { onEvent(FlightFormUiEvent.UpdateSeatNumber(it)) },
                label = { Text(stringResource(R.string.seat_number_label)) },
                placeholder = { Text(stringResource(R.string.seat_number_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters
                ),
                modifier = Modifier.fillMaxWidth()
            )
            RedknotDateSelectionField(
                date = uiState.date,
                label = stringResource(R.string.date_label),
                placeholder = stringResource(R.string.date_placeholder),
                hasError = uiState.dateError != null,
                supportingText = { uiState.dateError?.let { Text(it.toMessage(context)) } },
                onDateSelect = { onEvent(FlightFormUiEvent.UpdateDate(it)) },
                startDate = uiState.tripStartDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.default)
            )
            TimeSelectionField(
                value = uiState.time,
                label = stringResource(R.string.time_label),
                placeholder = stringResource(R.string.time_placeholder),
                hasError = uiState.timeError != null,
                supportingText = { uiState.timeError?.let { Text(it.toMessage(context)) } },
                onTimeSelect = { onEvent(FlightFormUiEvent.UpdateTime(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onEvent(FlightFormUiEvent.Submit(tripId)) },
                enabled = uiState.isFormValid && !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.default)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = stringResource(
                            if (uiState.isEditMode) R.string.update_item else R.string.add_item
                        ),
                        modifier = Modifier.padding(start = MaterialTheme.spacing.small)
                    )
                }
            }
        }
    }
}

