package com.luisfagundes.itinerary.presentation.screen

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import com.luisfagundes.trip.R
import com.luisfagundes.trip.tools.extensions.convertMillisToLocalDate
import com.luisfagundes.trip.tools.extensions.toFormattedString
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateSelectionField(
    value: LocalDate?,
    label: String,
    placeholder: String,
    hasError: Boolean,
    onDateSelect: (LocalDate?) -> Unit,
    supportingText: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value.toFormattedString(),
        onValueChange = {},
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
        modifier = modifier.pointerInput(Unit) {
            awaitEachGesture {
                awaitFirstDown(pass = PointerEventPass.Initial).consume()
                showDatePicker = true
            }
        }
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            confirmButton = {
                TextButton(onClick = {
                    onDateSelect(datePickerState.selectedDateMillis.convertMillisToLocalDate())
                    showDatePicker = false
                }) { Text(stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            onDismissRequest = { showDatePicker = false }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimeSelectionField(
    value: LocalTime?,
    label: String,
    placeholder: String,
    hasError: Boolean,
    onTimeSelect: (LocalTime) -> Unit,
    supportingText: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value.toFormattedString(),
        onValueChange = {},
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = stringResource(R.string.select_time_icon_description)
            )
        },
        readOnly = true,
        isError = hasError,
        supportingText = supportingText,
        modifier = modifier.pointerInput(Unit) {
            awaitEachGesture {
                awaitFirstDown(pass = PointerEventPass.Initial).consume()
                showTimePicker = true
            }
        }
    )

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState()
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelect(LocalTime.of(timePickerState.hour, timePickerState.minute))
                    showTimePicker = false
                }) { Text(stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            text = { TimePicker(state = timePickerState) }
        )
    }
}
