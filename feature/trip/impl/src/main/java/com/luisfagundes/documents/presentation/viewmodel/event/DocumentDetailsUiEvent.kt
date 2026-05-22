package com.luisfagundes.documents.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import com.luisfagundes.documents.domain.model.Document

internal sealed interface DocumentDetailsUiEvent : UiEvent {
    data class GetDocument(val documentId: Int) : DocumentDetailsUiEvent
    data class DeleteDocument(val document: Document) : DocumentDetailsUiEvent
    data object NavigateBack : DocumentDetailsUiEvent
}
