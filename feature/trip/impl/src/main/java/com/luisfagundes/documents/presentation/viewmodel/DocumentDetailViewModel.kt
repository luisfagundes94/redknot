package com.luisfagundes.documents.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.documents.domain.usecase.DeleteDocumentUseCase
import com.luisfagundes.documents.domain.usecase.GetDocumentByIdUseCase
import com.luisfagundes.documents.presentation.viewmodel.effect.DocumentDetailUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.DocumentDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DocumentDetailViewModel @Inject constructor(
    private val getDocumentByIdUseCase: GetDocumentByIdUseCase,
    private val deleteDocumentUseCase: DeleteDocumentUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<DocumentDetailUiState, DocumentDetailUiEffect>(
    initialState = DocumentDetailUiState.Loading
) {
    fun getDocument(documentId: Int) = viewModelScope.launch(dispatcher) {
        getDocumentByIdUseCase(documentId).fold(
            onSuccess = { document -> setState { DocumentDetailUiState.Content(document) } },
            onFailure = { throwable -> setState { DocumentDetailUiState.Error(throwable.message) } }
        )
    }

    fun deleteDocument() = viewModelScope.launch(dispatcher) {
        val state = getCurrentState()
        if (state !is DocumentDetailUiState.Content) return@launch

        deleteDocumentUseCase(state.document).fold(
            onSuccess = { sendEffect { DocumentDetailUiEffect.NavigateBack } },
            onFailure = {}
        )
    }
}
