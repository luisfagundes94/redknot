package com.luisfagundes.documents.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface DocumentDetailUiEffect : UiEffect {
    data object NavigateBack : DocumentDetailUiEffect
}
