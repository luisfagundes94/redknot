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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.common.presentation.mapper.toMessage
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotDateSelectionField
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.presentation.viewmodel.ActivityFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ActivityFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.event.ActivityFormUiEvent
import com.luisfagundes.itinerary.presentation.viewmodel.state.ActivityFormUiState
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.components.DeleteConfirmationDialog

@Composable
internal fun ActivityFormScreen(
    tripId: Int,
    itineraryItemId: String? = null,
    onBackClick: () -> Unit,
    onNavigateBackToTripDetails: () -> Unit,
    viewModel: ActivityFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(ActivityFormUiEvent.InitForm(tripId, itineraryItemId))
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is ActivityFormUiEffect.NavigateBack -> onBackClick()
            is ActivityFormUiEffect.NavigateBackToTripDetails -> onNavigateBackToTripDetails()
            is ActivityFormUiEffect.ShowErrorToast -> {
                Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    ActivityFormContent(
        tripId = tripId,
        uiState = uiState,
        onEvent = viewModel::dispatchEvent
    )
}

@Composable
private fun ActivityFormContent(
    tripId: Int,
    uiState: ActivityFormUiState,
    onEvent: (ActivityFormUiEvent) -> Unit
) {
    when (uiState) {
        is ActivityFormUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )
        is ActivityFormUiState.Content -> ActivityForm(
            tripId = tripId,
            uiState = uiState,
            onEvent = onEvent
        )
    }
}

@Composable
private fun ActivityForm(
    tripId: Int,
    uiState: ActivityFormUiState.Content,
    onEvent: (ActivityFormUiEvent) -> Unit
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
                onEvent(ActivityFormUiEvent.DeleteActivity)
            }
        )
    }

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_activity),
                onBackClick = { onEvent(ActivityFormUiEvent.NavigateBack) },
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
                .padding(horizontal = MaterialTheme.spacing.default)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { onEvent(ActivityFormUiEvent.UpdateTitle(it)) },
                label = { Text(stringResource(R.string.activity_title_label)) },
                placeholder = { Text(stringResource(R.string.activity_title_placeholder)) },
                singleLine = true,
                isError = uiState.titleError != null,
                supportingText = { uiState.titleError?.let { Text(it.toMessage(context)) } },
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
                value = uiState.description,
                onValueChange = { onEvent(ActivityFormUiEvent.UpdateDescription(it)) },
                label = { Text(stringResource(R.string.activity_description_label)) },
                placeholder = { Text(stringResource(R.string.activity_description_placeholder)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.location,
                onValueChange = { onEvent(ActivityFormUiEvent.UpdateLocation(it)) },
                label = { Text(stringResource(R.string.activity_location_label)) },
                placeholder = { Text(stringResource(R.string.activity_location_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.default)
            )
            RedknotDateSelectionField(
                date = uiState.date,
                label = stringResource(R.string.date_label),
                placeholder = stringResource(R.string.date_placeholder),
                hasError = uiState.dateError != null,
                supportingText = { uiState.dateError?.let { Text(it.toMessage(context)) } },
                onDateSelect = { onEvent(ActivityFormUiEvent.UpdateDate(it)) },
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
                onTimeSelect = { onEvent(ActivityFormUiEvent.UpdateTime(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onEvent(ActivityFormUiEvent.Submit(tripId)) },
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
