package com.luisfagundes.documents.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent

internal sealed interface DocumentsUiEvent : UiEvent {
    data class GetDocuments(val tripId: Int) : DocumentsUiEvent
    data object NavigateToAddDocument : DocumentsUiEvent
    data class NavigateToDocumentDetails(val documentId: Int) : DocumentsUiEvent
}
