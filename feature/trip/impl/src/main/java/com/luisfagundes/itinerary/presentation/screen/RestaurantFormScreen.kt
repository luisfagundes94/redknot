package com.luisfagundes.itinerary.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.common.presentation.mapper.toMessage
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotDateSelectionField
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.MealType
import com.luisfagundes.itinerary.presentation.components.MealTypeComboBox
import com.luisfagundes.itinerary.presentation.viewmodel.RestaurantFormViewModel
import com.luisfagundes.itinerary.presentation.viewmodel.effect.RestaurantFormUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.RestaurantFormUiState
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.components.DeleteConfirmationDialog
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun RestaurantFormScreen(
    tripId: Int,
    itineraryItemId: String? = null,
    onBackClick: () -> Unit,
    onNavigateBackToTripDetails: () -> Unit,
    viewModel: RestaurantFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initForm(tripId, itineraryItemId)
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is RestaurantFormUiEffect.NavigateBack -> onBackClick()
            is RestaurantFormUiEffect.NavigateBackToTripDetails -> onNavigateBackToTripDetails()
            is RestaurantFormUiEffect.ShowErrorToast -> {
                Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    when (uiState) {
        is RestaurantFormUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )
        is RestaurantFormUiState.Content -> RestaurantFormContent(
            uiState = uiState as RestaurantFormUiState.Content,
            onMealTypeChange = viewModel::onMealTypeChange,
            onNameChange = viewModel::onNameChange,
            onAddressChange = viewModel::onAddressChange,
            onDateChange = viewModel::onDateChange,
            onTimeChange = viewModel::onTimeChange,
            onSubmit = { viewModel.onSubmit(tripId) },
            onDelete = viewModel::onDelete,
            onBackClick = onBackClick
        )
    }
}

@Composable
private fun RestaurantFormContent(
    uiState: RestaurantFormUiState.Content,
    onMealTypeChange: (MealType) -> Unit,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onDateChange: (LocalDate?) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onSubmit: () -> Unit,
    onDelete: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            title = stringResource(R.string.delete_itinerary_item_dialog_title),
            message = stringResource(R.string.delete_itinerary_item_dialog_message),
            onDismissRequest = { showDeleteDialog = false },
            onDeleteClick = { showDeleteDialog = false; onDelete() }
        )
    }

    Scaffold(
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.create_restaurant),
                onBackClick = onBackClick,
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
                supportingText = { uiState.nameError?.let { Text(it.toMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.address,
                onValueChange = onAddressChange,
                label = { Text(stringResource(R.string.address_label)) },
                placeholder = { Text(stringResource(R.string.address_placeholder)) },
                singleLine = true,
                isError = uiState.addressError != null,
                supportingText = { uiState.addressError?.let { Text(it.toMessage(context)) } },
                modifier = Modifier.fillMaxWidth()
            )
            RedknotDateSelectionField(
                date = uiState.date,
                label = stringResource(R.string.date_label),
                placeholder = stringResource(R.string.date_placeholder),
                hasError = uiState.dateError != null,
                supportingText = { uiState.dateError?.let { Text(it.toMessage(context)) } },
                onDateSelect = onDateChange,
                initialDisplayedMonth = uiState.tripStartDate,
                modifier = Modifier.fillMaxWidth()
            )
            TimeSelectionField(
                value = uiState.time,
                label = stringResource(R.string.time_label),
                placeholder = stringResource(R.string.time_placeholder),
                hasError = uiState.timeError != null,
                supportingText = { uiState.timeError?.let { Text(it.toMessage(context)) } },
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
