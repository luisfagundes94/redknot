package com.luisfagundes.documents.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.core.common.presentation.navigation.Navigator
import com.luisfagundes.documents.presentation.screen.AddDocumentFormScreen
import com.luisfagundes.documents.presentation.screen.DocumentsScreen
import com.luisfagundes.trip.api.presentation.navigation.AddDocumentFormRoute
import com.luisfagundes.trip.api.presentation.navigation.DocumentsRoute

fun EntryProviderScope<NavKey>.documentsEntry(
    navigator: Navigator
) {
    entry<DocumentsRoute> { key ->
        DocumentsScreen(
            tripId = key.tripId,
            onNavigateToDocumentForm = {
                navigator.navigateTo(AddDocumentFormRoute(key.tripId))
            }
        )
    }
    entry<AddDocumentFormRoute> { key ->
        AddDocumentFormScreen(
            tripId = key.tripId,
            onNavigateBack = { navigator.goBack() }
        )
    }
}
