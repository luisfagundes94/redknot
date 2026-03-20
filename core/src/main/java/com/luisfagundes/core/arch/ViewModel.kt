package com.luisfagundes.core.arch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModel<State : UiState, Action : UiAction> : ViewModel() {
    private val initialState: State by lazy { initialState() }

    protected val state = MutableStateFlow(initialState)
    val uiState = state.asStateFlow()

    private val _uiAction = Channel<Action>()
    val uiAction = _uiAction.receiveAsFlow()
    abstract fun initialState(): State

    protected fun getCurrentState(): State = uiState.value

    protected fun setState(reducer: (State) -> State) {
        state.value = reducer(state.value)
    }

    protected inline fun <reified UiStateType : State> setStateOf(
        noinline reducer: (UiStateType) -> State
    ) {
        val currentState = uiState.value
        if (currentState is UiStateType) {
            state.update { reducer(currentState) }
        }
    }

    protected fun sendAction(action: Action) = viewModelScope.launch {
        runCatching {
            _uiAction.send(action)
        }.onFailure { throwable ->
            Log.w("Failed to send action: ${throwable.message}", throwable)
        }
    }
}