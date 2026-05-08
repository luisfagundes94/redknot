package com.luisfagundes.documents.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface AddDocumentFormUiEffect : UiEffect {
    data object NavigateBack : AddDocumentFormUiEffect
    data object LaunchCamera : AddDocumentFormUiEffect
    data object LaunchFilePicker : AddDocumentFormUiEffect
}
