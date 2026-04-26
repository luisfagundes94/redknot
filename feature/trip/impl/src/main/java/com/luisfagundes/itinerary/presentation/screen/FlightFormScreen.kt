package com.luisfagundes.itinerary.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.presentation.mapper.toErrorMessage
import com.luisfagundes.itinerary.presentation.viewmodel.FlightFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.FlightFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.FlightFormUiState
import com.luisfagundes.trip.R
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun FlightFormScreen(
    tripId: Int,
    onBackClick: () -> Unit,
    onNavigateBackToTripDetails: () -> Unit,
    viewModel: FlightFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectUiEffects(
        flow = viewModel.uiEffect,
        onEffect = { effect ->
            when (effect) {
                is FlightFormUiEffect.NavigateToTripDetails -> {
                    onNavigateBackToTripDetails()
                }
                is FlightFormUiEffect.ShowErrorToast -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_flight),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        FlightFormFields(
            uiState = uiState,
            onFlightNumberChange = viewModel::onFlightNumberChange,
            onCompanyNameChange = viewModel::onCompanyNameChange,
            onOriginChange = viewModel::onOriginChange,
            onDestinationChange = viewModel::onDestinationChange,
            onDurationChange = viewModel::onDurationChange,
            onSeatNumberChange = viewModel::onSeatNumberChange,
            onDateChange = viewModel::onDateChange,
            onTimeChange = viewModel::onTimeChange,
            onSubmit = { viewModel.onSubmit(tripId) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun FlightFormFields(
    uiState: FlightFormUiState,
    onFlightNumberChange: (String) -> Unit,
    onCompanyNameChange: (String) -> Unit,
    onOriginChange: (city: String) -> Unit,
    onDestinationChange: (city: String) -> Unit,
    onDurationChange: (hours: String, minutes: String) -> Unit,
    onSeatNumberChange: (String) -> Unit,
    onDateChange: (LocalDate?) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(horizontal = MaterialTheme.spacing.default)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = uiState.flightNumber,
            onValueChange = onFlightNumberChange,
            label = { Text(stringResource(R.string.flight_number_label)) },
            placeholder = { Text(stringResource(R.string.flight_number_placeholder)) },
            singleLine = true,
            isError = uiState.flightNumberError != null,
            supportingText = {
                uiState.flightNumberError?.let { Text(it.toErrorMessage(context)) }
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.companyName,
            onValueChange = onCompanyNameChange,
            label = { Text(stringResource(R.string.company_name_label)) },
            placeholder = { Text(stringResource(R.string.company_name_placeholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.originAirportCity,
            onValueChange = { onOriginChange(it) },
            label = { Text(stringResource(R.string.origin_airport_city_label)) },
            placeholder = { Text(stringResource(R.string.origin_airport_city_placeholder)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.default)
        )
        OutlinedTextField(
            value = uiState.destinationAirportCity,
            onValueChange = { onDestinationChange(it) },
            label = { Text(stringResource(R.string.destination_airport_city_label)) },
            placeholder = {
                Text(stringResource(R.string.destination_airport_city_placeholder))
            },
            singleLine = true,
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
                onValueChange = { onDurationChange(it, uiState.durationMinutes) },
                label = { Text(stringResource(R.string.flight_duration_hours_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = uiState.durationError != null,
                supportingText = {
                    uiState.durationError?.let { Text(it.toErrorMessage(context)) }
                },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = uiState.durationMinutes,
                onValueChange = { onDurationChange(uiState.durationHours, it) },
                label = { Text(stringResource(R.string.flight_duration_minutes_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = uiState.seatNumber,
            onValueChange = onSeatNumberChange,
            label = { Text(stringResource(R.string.seat_number_label)) },
            placeholder = { Text(stringResource(R.string.seat_number_placeholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        DateSelectionField(
            value = uiState.date,
            label = stringResource(R.string.date_label),
            placeholder = stringResource(R.string.date_placeholder),
            hasError = uiState.dateError != null,
            supportingText = { uiState.dateError?.let { Text(it.toErrorMessage(context)) } },
            onDateSelect = onDateChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.default)
        )
        TimeSelectionField(
            value = uiState.time,
            label = stringResource(R.string.time_label),
            placeholder = stringResource(R.string.time_placeholder),
            hasError = uiState.timeError != null,
            supportingText = { uiState.timeError?.let { Text(it.toErrorMessage(context)) } },
            onTimeSelect = onTimeChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSubmit,
            enabled = uiState.isFormValid && !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.default)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(
                    text = stringResource(R.string.add_item),
                    modifier = Modifier.padding(start = MaterialTheme.spacing.small)
                )
            }
        }
    }
}
