package com.luisfagundes.documents.presentation.viewmodel

import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.documents.presentation.viewmodel.effect.DocumentsUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.DocumentsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DocumentsViewModel @Inject constructor() :
    ViewModel<DocumentsUiState, DocumentsUiEffect>(
        initialState = DocumentsUiState.Empty
    ) {
    fun navigateToDocumentForm() {
        sendEffect { DocumentsUiEffect.NavigateToDocumentForm }
    }
}