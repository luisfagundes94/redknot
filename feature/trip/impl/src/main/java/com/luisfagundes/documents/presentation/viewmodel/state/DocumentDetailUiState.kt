package com.luisfagundes.documents.presentation.viewmodel.state

import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.documents.domain.model.Document

internal sealed interface DocumentDetailUiState : UiState {
    data object Loading : DocumentDetailUiState
    data class Content(val document: Document) : DocumentDetailUiState
    data class Error(val message: String?) : DocumentDetailUiState
}
