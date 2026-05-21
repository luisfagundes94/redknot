package com.luisfagundes.documents.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.domain.usecase.GetDocumentsByCategoryUseCase
import com.luisfagundes.documents.presentation.viewmodel.effect.DocumentsUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.DocumentsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DocumentsViewModel @Inject constructor(
    private val getDocumentsByCategoryIdUseCase: GetDocumentsByCategoryUseCase,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel<DocumentsUiState, DocumentsUiEffect>(
    initialState = DocumentsUiState.Empty
) {
    fun getDocuments(tripId: Int) = viewModelScope.launch(dispatcher) {
        getDocumentsByCategoryIdUseCase(tripId).fold(
            onSuccess = { setDocuments(it) },
            onFailure = {}
        )
    }
    fun navigateToDocumentForm() {
        sendEffect { DocumentsUiEffect.NavigateToDocumentForm }
    }

    fun navigateToDocumentDetail(documentId: Int) {
        sendEffect { DocumentsUiEffect.NavigateToDocumentDetail(documentId) }
    }

    private fun setDocuments(documents: Map<DocumentCategory, List<Document>>) {
        setState {
            if (documents.values.isNotEmpty()) DocumentsUiState.Content(documents)
            else DocumentsUiState.Empty
        }
    }
}