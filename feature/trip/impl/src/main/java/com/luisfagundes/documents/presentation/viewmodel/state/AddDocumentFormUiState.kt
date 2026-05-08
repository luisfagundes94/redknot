package com.luisfagundes.documents.presentation.viewmodel.state

import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.documents.domain.model.Attachment
import com.luisfagundes.documents.domain.model.DocumentCategory

internal data class AddDocumentFormUiState(
    val tripId: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: DocumentCategory = DocumentCategory.OTHER,
    val attachment: Attachment? = null,
    val isSaving: Boolean = false
) : UiState
