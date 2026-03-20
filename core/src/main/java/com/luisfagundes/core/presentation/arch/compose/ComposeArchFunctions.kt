package com.luisfagundes.core.presentation.arch.compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

@Composable
fun <T> CollectUiActions(
    flow: Flow<T>,
    onAction: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnAction by rememberUpdatedState(onAction)

    LaunchedEffect(lifecycleOwner.lifecycle, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow
                    .catch { Log.w("Failed to handle action: ${it.message}", it) }
                    .collect { currentOnAction(it) }

            }
        }
    }
}