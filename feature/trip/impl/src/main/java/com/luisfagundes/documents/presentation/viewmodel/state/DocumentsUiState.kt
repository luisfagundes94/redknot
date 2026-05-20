package com.luisfagundes.documents.presentation.viewmodel.state

import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory

internal sealed interface DocumentsUiState : UiState {
    data object Loading : DocumentsUiState
    data object Empty : DocumentsUiState
    data class Content(val documentsByCategory: Map<DocumentCategory, List<Document>>) : DocumentsUiState
}