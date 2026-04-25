package com.luisfagundes.itinerary.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.presentation.mapper.toErrorMessage
import com.luisfagundes.itinerary.presentation.viewmodel.AccommodationFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.AccommodationFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.AccommodationFormUiState
import com.luisfagundes.trip.R
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun AccommodationFormScreen(
    tripId: Int,
    onBackClick: () -> Unit,
    viewModel: AccommodationFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentOnBackClick by rememberUpdatedState(onBackClick)

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is AccommodationFormUiEffect.NavigateBack -> currentOnBackClick()
                is AccommodationFormUiEffect.ShowErrorToast -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    AccommodationFormContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onAddressChange = viewModel::onAddressChange,
        onCheckInTypeChange = viewModel::onCheckInTypeChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onSubmit = { viewModel.onSubmit(tripId) },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccommodationFormContent(
    uiState: AccommodationFormUiState,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onCheckInTypeChange: (CheckInType) -> Unit,
    onDateChange: (LocalDate?) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_accommodation),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.spacing.default)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.accommodation_name_label)) },
                placeholder = { Text(stringResource(R.string.accommodation_name_placeholder)) },
                singleLine = true,
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(it.toErrorMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.address,
                onValueChange = onAddressChange,
                label = { Text(stringResource(R.string.accommodation_address_label)) },
                placeholder = { Text(stringResource(R.string.accommodation_address_placeholder)) },
                singleLine = true,
                isError = uiState.addressError != null,
                supportingText = { uiState.addressError?.let { Text(it.toErrorMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.check_in_type_label),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = MaterialTheme.spacing.small)
            )
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.small)
            ) {
                CheckInType.entries.forEachIndexed { index, type ->
                    SegmentedButton(
                        selected = uiState.checkInType == type,
                        onClick = { onCheckInTypeChange(type) },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = CheckInType.entries.size
                        )
                    ) {
                        Text(
                            text = if (type == CheckInType.CHECK_IN) {
                                stringResource(R.string.check_in)
                            } else {
                                stringResource(R.string.check_out)
                            }
                        )
                    }
                }
            }
            DateSelectionField(
                value = uiState.date,
                label = stringResource(R.string.date_label),
                placeholder = stringResource(R.string.date_placeholder),
                hasError = uiState.dateError != null,
                supportingText = { uiState.dateError?.let { Text(it.toErrorMessage(context)) } },
                onDateSelect = onDateChange,
                modifier = Modifier.fillMaxWidth()
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
}
