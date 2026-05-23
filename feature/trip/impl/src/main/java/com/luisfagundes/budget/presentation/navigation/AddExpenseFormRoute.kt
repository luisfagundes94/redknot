package com.luisfagundes.budget.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal data class AddExpenseFormRoute(val tripId: Int) : NavKey
