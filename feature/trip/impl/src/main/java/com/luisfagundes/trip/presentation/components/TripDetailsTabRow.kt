package com.luisfagundes.trip.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.trip.presentation.model.TripDetailsTabs

@Composable
internal fun TripDetailsTabRow(
    modifier: Modifier = Modifier,
    onTabSelect: (TripDetailsTabs) -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
    ) {
        val selectedColor = MaterialTheme.colorScheme.primary
        val unselectedColor = MaterialTheme.colorScheme.onSurface

        TripDetailsTabs.entries.forEachIndexed { index, tab ->
            val tabColor = if (selectedTabIndex == index) selectedColor else unselectedColor

            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index; onTabSelect(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null,
                        tint = tabColor
                    )
                },
                text = {
                    Text(
                        text = stringResource(tab.titleResId),
                        color = tabColor
                    )
                }
            )
        }
    }
}