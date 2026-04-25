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
import com.luisfagundes.itinerary.domain.model.MealType
import com.luisfagundes.itinerary.presentation.components.MealTypeComboBox
import com.luisfagundes.itinerary.presentation.mapper.toErrorMessage
import com.luisfagundes.itinerary.presentation.viewmodel.RestaurantFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.RestaurantFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.RestaurantFormUiState
import com.luisfagundes.trip.R
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun RestaurantFormScreen(
    tripId: Int,
    onBackClick: () -> Unit,
    viewModel: RestaurantFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentOnBackClick by rememberUpdatedState(onBackClick)

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is RestaurantFormUiEffect.NavigateBack -> currentOnBackClick()
                is RestaurantFormUiEffect.ShowErrorToast -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    RestaurantFormContent(
        uiState = uiState,
        onMealTypeChange = viewModel::onMealTypeChange,
        onNameChange = viewModel::onNameChange,
        onAddressChange = viewModel::onAddressChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onSubmit = { viewModel.onSubmit(tripId) },
        onBackClick = onBackClick
    )
}

@Composable
private fun RestaurantFormContent(
    uiState: RestaurantFormUiState,
    onMealTypeChange: (MealType) -> Unit,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onDateChange: (LocalDate?) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_restaurant),
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
            MealTypeComboBox(
                selectedMealType = uiState.mealType,
                onMealTypeSelected = onMealTypeChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.default)
            )
            OutlinedTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.restaurant_name_label)) },
                placeholder = { Text(stringResource(R.string.restaurant_name_placeholder)) },
                singleLine = true,
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(it.toErrorMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.address,
                onValueChange = onAddressChange,
                label = { Text(stringResource(R.string.address_label)) },
                placeholder = { Text(stringResource(R.string.address_placeholder)) },
                singleLine = true,
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(it.toErrorMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
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
