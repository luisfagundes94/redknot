package com.luisfagundes.designsystem.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import com.luisfagundes.designsystem.R
import com.luisfagundes.designsystem.extensions.convertMillisToLocalDate
import com.luisfagundes.designsystem.extensions.toFormattedString
import com.luisfagundes.designsystem.extensions.toTimestampMillis
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedknotDateSelectionField(
    date: LocalDate?,
    label: String,
    placeholder: String,
    hasError: Boolean,
    onDateSelect: (LocalDate?) -> Unit,
    supportingText: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    startDate: LocalDate? = null,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = date.toFormattedString(),
        onValueChange = {},
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.select_date)
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
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = (startDate ?: date)?.toTimestampMillis(),
        )
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