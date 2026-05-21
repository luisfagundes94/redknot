package com.luisfagundes.common.presentation.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

internal fun Modifier.paddingExceptBottom(padding: Dp) = this.padding(
    top = padding,
    start = padding,
    end = padding
)