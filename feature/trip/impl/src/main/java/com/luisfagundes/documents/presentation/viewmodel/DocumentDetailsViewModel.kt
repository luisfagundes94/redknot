package com.luisfagundes.documents.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.usecase.DeleteDocumentUseCase
import com.luisfagundes.documents.domain.usecase.GetDocumentByIdUseCase
import com.luisfagundes.documents.presentation.viewmodel.effect.DocumentDetailUiEffect
import com.luisfagundes.documents.presentation.viewmodel.event.DocumentDetailsUiEvent
import com.luisfagundes.documents.presentation.viewmodel.state.DocumentDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DocumentDetailsViewModel @Inject constructor(
    private val getDocumentByIdUseCase: GetDocumentByIdUseCase,
    private val deleteDocumentUseCase: DeleteDocumentUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<DocumentDetailUiState, DocumentDetailsUiEvent, DocumentDetailUiEffect>(
    initialState = DocumentDetailUiState.Loading
) {
    override fun dispatchEvent(event: DocumentDetailsUiEvent) {
        when (event) {
            is DocumentDetailsUiEvent.GetDocument -> getDocument(event.documentId)
            is DocumentDetailsUiEvent.DeleteDocument -> deleteDocument(event.document)
            is DocumentDetailsUiEvent.NavigateBack -> navigateBack()
        }
    }

    private fun getDocument(documentId: Int) = viewModelScope.launch(dispatcher) {
        getDocumentByIdUseCase(documentId).fold(
            onSuccess = { document -> setState { DocumentDetailUiState.Content(document) } },
            onFailure = { throwable -> setState { DocumentDetailUiState.Error(throwable.message) } }
        )
    }

    private fun deleteDocument(document: Document) = viewModelScope.launch(dispatcher) {
        deleteDocumentUseCase(document).fold(
            onSuccess = { sendEffect { DocumentDetailUiEffect.NavigateBack } },
            onFailure = {}
        )
    }

    private fun navigateBack() {
        sendEffect { DocumentDetailUiEffect.NavigateBack }
    }
}
