package com.luisfagundes.budget.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import com.luisfagundes.budget.domain.model.ExpenseCategory

internal fun ExpenseCategory.toIcon(): ImageVector = when (this) {
    ExpenseCategory.TRANSPORT -> Icons.Default.LocalTaxi
    ExpenseCategory.LODGING -> Icons.Default.Hotel
    ExpenseCategory.FOOD -> Icons.Default.Restaurant
    ExpenseCategory.ACTIVITIES -> Icons.Default.Attractions
    ExpenseCategory.SHOPPING -> Icons.Default.ShoppingBag
    ExpenseCategory.OTHER -> Icons.Default.MoreHoriz
}