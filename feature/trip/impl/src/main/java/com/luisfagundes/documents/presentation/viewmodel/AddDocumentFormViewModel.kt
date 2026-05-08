package com.luisfagundes.documents.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.documents.domain.model.Attachment
import com.luisfagundes.documents.domain.model.AttachmentSource
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
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
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel<AddDocumentFormUiState, AddDocumentFormUiEffect>(
    initialState = AddDocumentFormUiState()
) {
    fun initForm(tripId: Int) {
        setState { it.copy(tripId = tripId) }
    }

    fun onCategorySelect(category: DocumentCategory) {
        setState { it.copy(category = category) }
    }

    fun onTitleChange(title: String) {
        setState { it.copy(title = title) }
    }

    fun onDescriptionChange(description: String) {
        setState { it.copy(description = description) }
    }

    fun onTakePhotoClick() {
        sendEffect { AddDocumentFormUiEffect.LaunchCamera }
    }

    fun onUploadFileClick() {
        sendEffect { AddDocumentFormUiEffect.LaunchFilePicker }
    }

    fun onPhotoTaken(uri: Uri) {
        setState { it.copy(attachment = Attachment.Pending(uri, AttachmentSource.Camera)) }
    }

    fun onAttachmentRemove() {
        setState { it.copy(attachment = null) }
    }

    fun onSaveDocumentClick() {
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

    fun onBackClick() {
        sendEffect { AddDocumentFormUiEffect.NavigateBack }
    }
}
