package com.luisfagundes.budget.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.budget.presentation.screen.AddExpenseFormScreen

internal fun EntryProviderScope<NavKey>.budgetEntries(
    goBack: () -> Unit
) {
    entry<AddExpenseFormRoute> { key ->
        AddExpenseFormScreen(
            tripId = key.tripId,
            onNavigateBack = { goBack() }
        )
    }
}
