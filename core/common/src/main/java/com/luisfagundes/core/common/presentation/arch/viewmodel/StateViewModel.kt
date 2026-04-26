package com.luisfagundes.core.common.presentation.arch.viewmodel

import androidx.lifecycle.ViewModel
import com.luisfagundes.core.common.presentation.arch.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class StateViewModel<State: UiState> : ViewModel() {
    private val initialState: State by lazy { initialState() }

    protected val state = MutableStateFlow(initialState)
    val uiState = state.asStateFlow()

    abstract fun initialState(): State

    protected fun getCurrentState(): State = uiState.value

    protected fun setState(reducer: (State) -> State) {
        state.update(reducer)
    }

    protected inline fun <reified UiStateType : State> setStateOf(
        noinline reducer: (UiStateType) -> State
    ) {
        state.update { current ->
            if (current is UiStateType) reducer(current) else current
        }
    }
}