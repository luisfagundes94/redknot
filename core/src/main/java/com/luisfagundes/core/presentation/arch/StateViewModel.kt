package com.luisfagundes.core.presentation.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class StateViewModel<State: UiState, Event: UiEvent> : ViewModel() {
    private val initialState: State by lazy { initialState() }

    protected val state = MutableStateFlow(initialState)
    val uiState = state.asStateFlow()

    abstract fun initialState(): State

    abstract fun dispatchEvent(event: Event)

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
}