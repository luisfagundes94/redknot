package com.luisfagundes.documents.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.documents.presentation.screen.AddDocumentFormScreen
import com.luisfagundes.documents.presentation.screen.DocumentsScreen

internal fun EntryProviderScope<NavKey>.documentsEntries(
    navigateTo: (NavKey) -> Unit,
    goBack: () -> Unit,
) {
    entry<DocumentsRoute> { key ->
        DocumentsScreen(
            tripId = key.tripId,
            onNavigateToDocumentForm = { navigateTo(AddDocumentFormRoute(key.tripId)) }
        )
    }
    entry<AddDocumentFormRoute> { key ->
        AddDocumentFormScreen(
            tripId = key.tripId,
            onNavigateBack = { goBack() }
        )
    }
}
