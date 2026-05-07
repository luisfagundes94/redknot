package com.luisfagundes.documents.presentation.viewmodel.state

import com.luisfagundes.core.common.presentation.arch.state.UiState

internal sealed interface DocumentsUiState : UiState {
    data object Loading : DocumentsUiState
    data object Empty : DocumentsUiState
    data object Content : DocumentsUiState
}