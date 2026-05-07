package com.luisfagundes.documents.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.core.common.presentation.navigation.Navigator
import com.luisfagundes.documents.presentation.screen.DocumentsScreen
import com.luisfagundes.trip.api.presentation.navigation.DocumentsRoute

fun EntryProviderScope<NavKey>.documentsEntry(
    navigator: Navigator
) {
    entry<DocumentsRoute> { key ->
        DocumentsScreen(
            onNavigateToDocumentForm = {}
        )
    }
}
