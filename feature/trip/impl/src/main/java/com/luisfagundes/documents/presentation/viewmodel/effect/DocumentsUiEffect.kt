package com.luisfagundes.documents.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface DocumentsUiEffect : UiEffect {
    data object NavigateToDocumentForm : DocumentsUiEffect
}