package com.luisfagundes.documents.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.documents.domain.model.AttachmentSource
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.domain.usecase.ResolveAttachmentUseCase
import com.luisfagundes.documents.domain.usecase.SaveDocumentUseCase
import com.luisfagundes.documents.presentation.viewmodel.effect.AddDocumentFormUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.AddDocumentFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddDocumentFormViewModel @Inject constructor(
    private val saveDocumentUseCase: SaveDocumentUseCase,
    private val resolveAttachmentUseCase: ResolveAttachmentUseCase,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel<AddDocumentFormUiState, AddDocumentFormUiEffect>(
    initialState = AddDocumentFormUiState()
) {
    fun initForm(tripId: Int) {
        setState { it.copy(tripId = tripId) }
    }

    fun updateCategory(category: DocumentCategory) {
        setState { it.copy(category = category) }
    }

    fun updateTitle(title: String) {
        setState { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        setState { it.copy(description = description) }
    }

    fun takePhoto() {
        sendEffect { AddDocumentFormUiEffect.LaunchCamera }
    }

    fun uploadFile() {
        sendEffect { AddDocumentFormUiEffect.LaunchFilePicker }
    }

    fun handlePhoto(uri: Uri) {
        viewModelScope.launch(ioDispatcher) {
            val attachment = resolveAttachmentUseCase(uri, AttachmentSource.Camera)
            setState { it.copy(attachment = attachment) }
        }
    }

    fun handleFile(uri: Uri) {
        viewModelScope.launch(ioDispatcher) {
            val attachment = resolveAttachmentUseCase(uri, AttachmentSource.FilePicker)
            setState { it.copy(attachment = attachment) }
        }
    }

    fun removeAttachment() {
        setState { it.copy(attachment = null) }
    }

    fun saveDocument() {
        val state = getCurrentState()
        val attachment = state.attachment ?: return

        viewModelScope.launch(ioDispatcher) {
            setState { it.copy(isSaving = true) }

            val document = Document(
                tripId = state.tripId,
                title = state.title,
                description = state.description,
                category = state.category,
                attachment = attachment
            )

            saveDocumentUseCase(document)
                .onSuccess { sendEffect { AddDocumentFormUiEffect.NavigateBack } }
                .onFailure { setState { it.copy(isSaving = false) } }
        }
    }

    fun navigateBack() {
        sendEffect { AddDocumentFormUiEffect.NavigateBack }
    }
}
