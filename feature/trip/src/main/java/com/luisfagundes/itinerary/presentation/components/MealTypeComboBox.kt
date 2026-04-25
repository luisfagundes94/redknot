package com.luisfagundes.itinerary.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.itinerary.domain.model.MealType
import com.luisfagundes.trip.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MealTypeComboBox(
    selectedMealType: MealType?,
    onMealTypeSelected: (MealType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedMealType?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.meal_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            MealType.entries.forEach { mealType ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = mealType.name.lowercase().replaceFirstChar { it.uppercase() }
                        )
                    },
                    onClick = {
                        onMealTypeSelected(mealType)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}