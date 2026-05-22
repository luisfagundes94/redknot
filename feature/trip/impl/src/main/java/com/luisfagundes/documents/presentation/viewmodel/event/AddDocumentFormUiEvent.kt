package com.luisfagundes.documents.presentation.viewmodel.event

import android.net.Uri
import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import com.luisfagundes.documents.domain.model.DocumentCategory

internal sealed interface AddDocumentFormUiEvent : UiEvent {
    data class InitForm(val tripId: Int) : AddDocumentFormUiEvent
    data class UpdateCategory(val category: DocumentCategory) : AddDocumentFormUiEvent
    data class UpdateTitle(val title: String) : AddDocumentFormUiEvent
    data class UpdateDescription(val description: String) : AddDocumentFormUiEvent
    data object TakePhoto : AddDocumentFormUiEvent
    data object UploadFile : AddDocumentFormUiEvent
    data class HandlePhoto(val uri: Uri) : AddDocumentFormUiEvent
    data class HandleFile(val uri: Uri) : AddDocumentFormUiEvent
    data object RemoveAttachment : AddDocumentFormUiEvent
    data object SaveDocument : AddDocumentFormUiEvent
    data object NavigateBack : AddDocumentFormUiEvent
}
