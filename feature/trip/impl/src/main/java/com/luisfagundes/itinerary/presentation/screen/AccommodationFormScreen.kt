package com.luisfagundes.itinerary.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.common.presentation.mapper.toMessage
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotDateSelectionField
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.presentation.viewmodel.AccommodationFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.AccommodationFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.event.AccommodationFormUiEvent
import com.luisfagundes.itinerary.presentation.viewmodel.state.AccommodationFormUiState
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.components.DeleteConfirmationDialog

@Composable
internal fun AccommodationFormScreen(
    tripId: Int,
    itineraryItemId: String? = null,
    onBackClick: () -> Unit,
    onNavigateBackToTripDetails: () -> Unit,
    viewModel: AccommodationFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(AccommodationFormUiEvent.InitForm(tripId, itineraryItemId))
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is AccommodationFormUiEffect.NavigateBack -> onBackClick()
            is AccommodationFormUiEffect.NavigateBackToTripDetails -> onNavigateBackToTripDetails()
            is AccommodationFormUiEffect.ShowErrorToast -> {
                Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    AccommodationFormContent(
        tripId = tripId,
        uiState = uiState,
        onEvent = viewModel::dispatchEvent,
    )
}

@Composable
private fun AccommodationFormContent(
    tripId: Int,
    uiState: AccommodationFormUiState,
    onEvent: (AccommodationFormUiEvent) -> Unit
) {
    when (uiState) {
        is AccommodationFormUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )
        is AccommodationFormUiState.Content -> AccommodationForm(
            tripId = tripId,
            uiState = uiState,
            onEvent = onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccommodationForm(
    tripId: Int,
    uiState: AccommodationFormUiState.Content,
    onEvent: (AccommodationFormUiEvent) -> Unit
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
                showDeleteDialog = false;
                onEvent(AccommodationFormUiEvent.DeleteAccommodation)
            }
        )
    }

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_accommodation),
                onBackClick = { onEvent(AccommodationFormUiEvent.NavigateBack) },
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
                .padding(horizontal = MaterialTheme.spacing.default)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { onEvent(AccommodationFormUiEvent.UpdateName(it)) },
                label = { Text(stringResource(R.string.accommodation_name_label)) },
                placeholder = { Text(stringResource(R.string.accommodation_name_placeholder)) },
                singleLine = true,
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(it.toMessage(context)) } },
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
                value = uiState.address,
                onValueChange = { onEvent(AccommodationFormUiEvent.UpdateAddress(it)) },
                label = { Text(stringResource(R.string.accommodation_address_label)) },
                placeholder = { Text(stringResource(R.string.accommodation_address_placeholder)) },
                singleLine = true,
                isError = uiState.addressError != null,
                supportingText = { uiState.addressError?.let { Text(it.toMessage(context)) } },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.check_in_type_label),
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
            )
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.small)
            ) {
                CheckInType.entries.forEachIndexed { index, type ->
                    SegmentedButton(
                        selected = uiState.checkInType == type,
                        onClick = { onEvent(AccommodationFormUiEvent.UpdateCheckInType(type)) },
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
            RedknotDateSelectionField(
                date = uiState.date,
                label = stringResource(R.string.date_label),
                placeholder = stringResource(R.string.date_placeholder),
                hasError = uiState.dateError != null,
                supportingText = { uiState.dateError?.let { Text(it.toMessage(context)) } },
                onDateSelect = { onEvent(AccommodationFormUiEvent.UpdateDate(it)) },
                startDate = uiState.tripStartDate,
                modifier = Modifier.fillMaxWidth()
            )
            TimeSelectionField(
                value = uiState.time,
                label = stringResource(R.string.time_label),
                placeholder = stringResource(R.string.time_placeholder),
                hasError = uiState.timeError != null,
                supportingText = { uiState.timeError?.let { Text(it.toMessage(context)) } },
                onTimeSelect = { onEvent(AccommodationFormUiEvent.UpdateTime(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onEvent(AccommodationFormUiEvent.Submit(tripId)) },
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
