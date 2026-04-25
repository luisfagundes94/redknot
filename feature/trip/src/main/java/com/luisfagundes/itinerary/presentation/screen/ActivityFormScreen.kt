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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.luisfagundes.itinerary.presentation.mapper.toErrorMessage
import com.luisfagundes.itinerary.presentation.viewmodel.ActivityFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ActivityFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ActivityFormUiState
import com.luisfagundes.trip.R
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun ActivityFormScreen(
    tripId: Int,
    onBackClick: () -> Unit,
    viewModel: ActivityFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentOnBackClick by rememberUpdatedState(onBackClick)

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ActivityFormUiEffect.NavigateBack -> currentOnBackClick()
                is ActivityFormUiEffect.ShowErrorToast -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    ActivityFormContent(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onLocationChange = viewModel::onLocationChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onSubmit = { viewModel.onSubmit(tripId) },
        onBackClick = onBackClick
    )
}

@Composable
private fun ActivityFormContent(
    uiState: ActivityFormUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onDateChange: (LocalDate?) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_activity),
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
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text(stringResource(R.string.activity_title_label)) },
                placeholder = { Text(stringResource(R.string.activity_title_placeholder)) },
                singleLine = true,
                isError = uiState.titleError != null,
                supportingText = { uiState.titleError?.let { Text(it.toErrorMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = { Text(stringResource(R.string.activity_description_label)) },
                placeholder = { Text(stringResource(R.string.activity_description_placeholder)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.location,
                onValueChange = onLocationChange,
                label = { Text(stringResource(R.string.activity_location_label)) },
                placeholder = { Text(stringResource(R.string.activity_location_placeholder)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.default)
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
}
