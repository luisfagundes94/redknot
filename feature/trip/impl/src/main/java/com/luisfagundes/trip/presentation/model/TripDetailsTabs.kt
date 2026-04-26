package com.luisfagundes.trip.presentation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import com.luisfagundes.trip.R

internal enum class TripDetailsTabs(
    @param:StringRes val titleResId: Int,
    val icon: ImageVector
) {
    ITINERARY(
        titleResId = R.string.itinerary,
        icon = Icons.Default.Timeline
    ),
    BUDGET(
        titleResId = R.string.budget,
        icon = Icons.Default.AttachMoney
    ),
    DOCUMENTS(
        titleResId = R.string.documents,
        icon = Icons.Default.Description
    )
}